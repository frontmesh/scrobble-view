package com.frontmatic.scrobbleview.data.repository

import com.frontmatic.scrobbleview.data.model.User

interface LocalDataSource {
    suspend fun getUserInfo(): User?
    suspend fun saveUserInfo(user: User)
}