package com.frontmatic.scrobbleview.data.repository.impl

import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.model.User
import com.frontmatic.scrobbleview.data.repository.LocalDataSource

class LocalDataSourceImpl(database: ScrobbleDatabase): LocalDataSource {
    private val userDao = database.userDao()
    private val friendDao = database.friendDao()
    private val recentTrackDao = database.recentTrackDao()

    override suspend fun getUserInfoByName(name: String): User? {
        return userDao.getUserInfoByName(name)
    }

    override suspend fun saveUserInfo(user: User) {
        userDao.addUser(user)
    }

    override suspend fun deleteAllFriends() {
        friendDao.deleteAll()
    }

    override suspend fun deleteAllRecentTracks() {
        recentTrackDao.deleteAll()
    }
}