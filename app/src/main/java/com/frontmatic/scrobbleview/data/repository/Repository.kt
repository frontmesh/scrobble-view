package com.frontmatic.scrobbleview.data.repository

import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {
    fun getAllFriends() = remote.getAllFriends()

    fun getUserInfo(username: String) = remote.getUserInfo(username)

    suspend fun saveUsername(username: String) {
        dataStore.saveUsername(username)
    }

    fun getUsername() = dataStore.getUsername()
}