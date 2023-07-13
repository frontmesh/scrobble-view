package com.frontmatic.scrobbleview.data.repository.impl

import com.frontmatic.scrobbleview.data.ScrobbleDatabase
import com.frontmatic.scrobbleview.data.model.User
import com.frontmatic.scrobbleview.data.repository.LocalDataSource

class LocalDataSourceImpl(database: ScrobbleDatabase): LocalDataSource {
    private val userDao = database.userDao()

    override suspend fun getUserInfo(): User? {
        return userDao.getSelectedUser()
    }

    override suspend fun saveUserInfo(user: User) {
        userDao.addUser(user)
    }
}