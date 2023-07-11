package com.frontmatic.scrobbleview.data.repository

import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.model.Friend
import com.frontmatic.scrobbleview.data.model.User
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllFriends(): Flow<PagingData<Friend>>

}