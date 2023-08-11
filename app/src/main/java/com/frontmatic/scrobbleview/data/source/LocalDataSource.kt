package com.frontmatic.scrobbleview.data.source

import com.frontmatic.scrobbleview.data.model.User

interface LocalDataSource {
    suspend fun getUserInfoByName(name: String): User?
    suspend fun saveUserInfo(user: User)
    suspend fun deleteAllFriends()
    suspend fun deleteAllRecentTracks()

    suspend fun deleteAllTopTracks()
}