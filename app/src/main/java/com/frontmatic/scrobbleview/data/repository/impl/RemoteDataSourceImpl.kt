package com.frontmatic.scrobbleview.data.repository.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.model.RecentTrack
import com.frontmatic.scrobbleview.data.paging.FriendsRemoteMediator
import com.frontmatic.scrobbleview.data.paging.RecentTracksRemoteMediator
import com.frontmatic.scrobbleview.data.repository.DataStoreOperations
import com.frontmatic.scrobbleview.data.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow


@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val api: LastFMApi,
    private val database: ScrobbleDatabase,
    private val datastore: DataStoreOperations
): RemoteDataSource {
    private val friendDao = database.friendDao()
    private val recentTrackDao = database.recentTrackDao()

    override fun getAllFriends(): Flow<PagingData<Friend>> {
        return Pager(
            config = PagingConfig(pageSize = 50, initialLoadSize = 50 * 2, enablePlaceholders = true),
            remoteMediator = FriendsRemoteMediator(api, database, datastore),
            pagingSourceFactory = { friendDao.getAll() }
        ).flow
    }


    override fun getAllRecentTracks(): Flow<PagingData<RecentTrack>> {
        return Pager(
            config = PagingConfig(pageSize = 50, initialLoadSize = 50 * 2, enablePlaceholders = true),
            remoteMediator = RecentTracksRemoteMediator(api, database, datastore),
            pagingSourceFactory = { recentTrackDao.getAllRecentTracks() }
        ).flow
    }

}