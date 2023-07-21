package com.frontmatic.scrobbleview.data.repository

import com.frontmatic.scrobbleview.data.model.User
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStore: DataStoreOperations
) {
    fun getAllFriends() = remote.getAllFriends()

    suspend fun deleteAllFriends() = localDataSource.deleteAllFriends()

    fun getAllRecentTracks() = remote.getAllRecentTracks()

    suspend fun deleteAllRecentTracks() = localDataSource.deleteAllRecentTracks()

    suspend fun getUserInfoByName(name: String) = localDataSource.getUserInfoByName(name)

    suspend fun saveUserInfo(user: User) {
        localDataSource.saveUserInfo(user)
    }

    suspend fun saveUsername(username: String) {
        dataStore.saveUsername(username)
    }

    suspend fun saveUserChanged(userChanged: Boolean) {
        dataStore.saveUserChanged(userChanged)
    }

    fun getUserChanged() = dataStore.getUserChanged()

    fun getUsername() = dataStore.getUsername()
}