package com.frontmatic.scrobbleview.data.repository

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.StoreBuilder
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.data.model.User
import com.frontmatic.scrobbleview.data.source.DataStoreOperations
import com.frontmatic.scrobbleview.data.source.LocalDataSource
import com.frontmatic.scrobbleview.data.source.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStore: DataStoreOperations
) {
    fun getAllFriends() = remote.getAllFriends()

    suspend fun deleteAllFriends() = localDataSource.deleteAllFriends()

    fun getAllRecentTracks() = remote.getAllRecentTracks()

    fun getAllTopTracks(period: RequestPeriod) = remote.getAllTopTracks(period)


    suspend fun deleteAllTopTracks() = localDataSource.deleteAllTopTracks()

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