package com.frontmatic.scrobbleview.data.source

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveUsername (username: String)
    suspend fun saveUserChanged (userChanged: Boolean)
    fun getUsername (): Flow<String>
    fun getUserChanged (): Flow<Boolean>
}