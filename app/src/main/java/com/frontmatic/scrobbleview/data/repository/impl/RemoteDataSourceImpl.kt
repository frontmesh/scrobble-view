package com.frontmatic.scrobbleview.data.repository.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.LastFmRemoteMediator
import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.api.LastFMApi
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.model.User
import com.frontmatic.scrobbleview.data.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow


class RemoteDataSourceImpl(
    private val api: LastFMApi,
    private val database: ScrobbleDatabase
): RemoteDataSource {
    private val friendDao = database.friendDao()
    private val userDao = database.userDao()

    @ExperimentalPagingApi
    override fun getAllFriends(): Flow<PagingData<Friend>> {
        val pagingSourceFactory = { friendDao.getAllFriends() }

        return Pager(
            config = PagingConfig(pageSize = 50, enablePlaceholders = false),
            remoteMediator = LastFmRemoteMediator(api, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}