package com.frontmatic.scrobbleview.data.repository

import com.frontmatic.scrobbleview.data.model.User

interface LocalDataSource {
    suspend fun getUserInfo(username: String): User?
    suspend fun saveUserInfo(user: User)
}