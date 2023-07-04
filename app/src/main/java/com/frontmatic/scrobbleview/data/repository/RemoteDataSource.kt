package com.frontmatic.scrobbleview.data.repository

import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.model.Friend
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllFriends(): Flow<PagingData<Friend>>
}