package com.frontmatic.scrobbleview.data.repository

import com.frontmatic.scrobbleview.data.model.User
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStore: DataStoreOperations
) {
    fun getAllFriends() = remote.getAllFriends()

    suspend fun getUserInfo() = localDataSource.getUserInfo()

    suspend fun saveUserInfo(user: User) {
        localDataSource.saveUserInfo(user)
    }

    suspend fun saveUsername(username: String) {
        dataStore.saveUsername(username)
    }

    fun getUsername() = dataStore.getUsername()
}