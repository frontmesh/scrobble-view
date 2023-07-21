package com.frontmatic.scrobbleview.data.api

import com.frontmatic.scrobbleview.data.model.response.FriendApiResponse
import com.frontmatic.scrobbleview.data.model.UserApiResponse
import com.frontmatic.scrobbleview.data.model.response.RecentTracksApiResponse
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


    @GET(".")
    suspend fun getRecentTracks(
        @Query("method") method: String = "user.getrecenttracks",
        @Query("user") user: String,
        @Query("page") page: Int?
    ): RecentTracksApiResponse
}