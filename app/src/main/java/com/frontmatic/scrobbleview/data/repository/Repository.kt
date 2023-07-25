package com.frontmatic.scrobbleview.data.repository

import androidx.paging.PagingData
import com.frontmatic.scrobbleview.data.api.RequestPeriod
import com.frontmatic.scrobbleview.data.model.TopTrack
import com.frontmatic.scrobbleview.data.model.User
import kotlinx.coroutines.flow.Flow
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