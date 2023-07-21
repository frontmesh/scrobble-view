package com.frontmatic.scrobbleview.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.model.RecentTrack
import com.frontmatic.scrobbleview.data.model.RecentTracksRemoteKeys
import com.frontmatic.scrobbleview.data.repository.DataStoreOperations
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecentTracksRemoteMediator @Inject constructor(
    private val api: LastFMApi,
    private val database: ScrobbleDatabase,
    private val dataStore: DataStoreOperations
) : RemoteMediator<Int, RecentTrack>() {
    private val userDao = database.userDao()
    private val recenTracksDao = database.recentTrackDao()
    private val recentTracksRemoteKeysDao = database.recentTracksRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = recentTracksRemoteKeysDao.getFirstRemoteKey()?.lastUpdated ?: 0
        val cacheTimeout = 2 // 2 minutes

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60

        val userChanged = dataStore.getUserChanged().first()

        return if (diffInMinutes.toInt() >= cacheTimeout || userChanged) {
            dataStore.saveUserChanged(false)
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, RecentTrack>): MediatorResult {
        return try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)

                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val user = database.withTransaction {
                val storedUsername = dataStore.getUsername().first()
                userDao.getUserInfoByName(storedUsername)
            }

            if (user != null) {
                val res = api.getRecentTracks(page = page, user = user.name)

                val response = res.recenttracks

                if (response.track.isNotEmpty()) {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            recenTracksDao.deleteAll()
                            recentTracksRemoteKeysDao.deleteAllRemoteKeys()
                        }

                        val prevPage = if (response.attr.page > 1) response.attr.page - 1 else null
                        val nextPage =
                            if (response.attr.page < response.attr.totalPages) response.attr.page + 1 else null

                        val keys = response.track.map {
                            RecentTracksRemoteKeys(
                                name = it.name,
                                prevPage = prevPage,
                                nextPage = nextPage
                            )
                        }

                        recentTracksRemoteKeysDao.addAll(keys)
                        recenTracksDao.addRecentTracks(response.track as MutableList<RecentTrack>)
                    }
                }
                MediatorResult.Success(endOfPaginationReached = response.attr.page == response.attr.totalPages)
            } else {
                MediatorResult.Error(Exception("User not found"))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RecentTrack>): RecentTracksRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                recentTracksRemoteKeysDao.getRemoteKeys(name)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RecentTrack>): RecentTracksRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { track ->
            recentTracksRemoteKeysDao.getRemoteKeys(track.name)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RecentTrack>): RecentTracksRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { track ->
            recentTracksRemoteKeysDao.getRemoteKeys(track.name)
        }
    }

}