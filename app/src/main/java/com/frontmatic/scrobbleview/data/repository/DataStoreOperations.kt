package com.frontmatic.scrobbleview.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveUsername (username: String)
    suspend fun saveUserChanged (userChanged: Boolean)
    fun getUsername (): Flow<String>
    fun getUserChanged (): Flow<Boolean>
}