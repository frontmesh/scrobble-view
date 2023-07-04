package com.frontmatic.scrobbleview.data.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveUsername (username: String)
    fun getUsername (): Flow<String>
}