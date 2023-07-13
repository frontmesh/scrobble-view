package com.frontmatic.scrobbleview.data.api

import com.frontmatic.scrobbleview.BuildConfig
import com.frontmatic.scrobbleview.data.model.FriendApiResponse
import com.frontmatic.scrobbleview.data.model.UserApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMApi {

    @GET(".")
    suspend fun getFriends(
        @Query("method") method: String = "user.getfriends",
        @Query("user") user: String,
        @Query("page") page: Int?
    ): FriendApiResponse

    @GET(".")
    suspend fun getUserInfo(
        @Query("method") method: String = "user.getinfo",
        @Query("user") user: String
    ): Response<UserApiResponse>
}