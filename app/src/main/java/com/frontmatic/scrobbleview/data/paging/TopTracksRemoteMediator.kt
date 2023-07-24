package com.frontmatic.scrobbleview.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.data.model.TopTrack
import com.frontmatic.scrobbleview.data.model.TopTracksRemoteKeys
import com.frontmatic.scrobbleview.data.repository.DataStoreOperations
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TopTracksRemoteMediator  @Inject constructor(
    private val api: LastFMApi,
    private val database: ScrobbleDatabase,
    private val dataStore: DataStoreOperations
): RemoteMediator<Int, TopTrack>() {
    private val userDao = database.userDao()
    private val topTrackDao = database.topTrackDao()
    private val topTracksRemoteKeysDao = database.topTracksRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = topTracksRemoteKeysDao.getFirstRemoteKey()?.lastUpdated ?: 0
        val cacheTimeout = 1 * 60 * 24 * 7 // 7 days in minutes

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60

        val userChanged = dataStore.getUserChanged().first()

        return if (diffInMinutes.toInt() >= cacheTimeout || userChanged) {
            dataStore.saveUserChanged(false)
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopTrack>
    ): MediatorResult {
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
                val res = api.getTopTracks(page = page, user = user.name, period = RequestPeriod.SEVEN_DAY)
                val response = res.toptracks

                if (response.track.isNotEmpty()) {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            topTracksRemoteKeysDao.deleteAll()
                            topTrackDao.deleteAll()
                        }

                        val prevPage = if (response.attr.page > 1) response.attr.page - 1 else null
                        val nextPage =
                            if (response.attr.page < response.attr.totalPages) response.attr.page + 1 else null
                        val keys = response.track.map {
                            TopTracksRemoteKeys(name = it.name, prevPage = prevPage, nextPage = nextPage)
                        }
                        topTracksRemoteKeysDao.addAll(keys)
                        topTrackDao.addAll(response.track)
                    }
                    MediatorResult.Success(endOfPaginationReached = response.attr.page == response.attr.totalPages)
                } else {
                    MediatorResult.Success(endOfPaginationReached = true)
                }
            } else {
                MediatorResult.Error(Exception("User not found"))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

        private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TopTrack>): TopTracksRemoteKeys? {
            return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.name?.let { name ->
                    topTracksRemoteKeysDao.getRemoteKeys(name)
                }
            }
        }

        private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TopTrack>): TopTracksRemoteKeys? {
            return state.pages.firstOrNull {
                it.data.isNotEmpty()
            }?.data?.firstOrNull()?.let { track ->
                topTracksRemoteKeysDao.getRemoteKeys(track.name)
            }
        }

        private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TopTrack>): TopTracksRemoteKeys? {
            return state.pages.lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()?.let { track ->
                topTracksRemoteKeysDao.getRemoteKeys(track.name)
            }
        }
}