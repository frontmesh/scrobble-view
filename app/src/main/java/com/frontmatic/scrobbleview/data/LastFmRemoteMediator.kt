package com.frontmatic.scrobbleview.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.model.FriendRemoteKeys
import javax.inject.Inject

@ExperimentalPagingApi
class LastFmRemoteMediator @Inject constructor(
    private val api: LastFMApi,
    private val database: ScrobbleDatabase,
) : RemoteMediator<Int, Friend>() {
    private val friendDao = database.friendDao()
    private val friendRemoteKeysDao = database.friendRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Friend>): MediatorResult {
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

            val res = api.getFriends(page = page)

            val response = res.friends

            if (response.user.isNotEmpty()) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        friendDao.deleteAllFriends()
                        friendRemoteKeysDao.deleteAllRemoteKeys()
                    }

                    val prevPage = if (response.attr.page > 1) response.attr.page - 1 else null
                    val nextPage = if (response.attr.page < response.attr.totalPages) response.attr.page + 1 else null

                    val keys = response.user.map {
                        FriendRemoteKeys(name = it.name, prevPage = prevPage, nextPage = nextPage)
                    }

                    friendRemoteKeysDao.addAll(keys)
                    friendDao.addFriends(response.user)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.attr.page == response.attr.totalPages)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Friend>): FriendRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                friendRemoteKeysDao.getRemoteKeys(name)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Friend>): FriendRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { friend ->
            friendRemoteKeysDao.getRemoteKeys(friend.name)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Friend>): FriendRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { friend ->
            friendRemoteKeysDao.getRemoteKeys(friend.name)
        }
    }

}