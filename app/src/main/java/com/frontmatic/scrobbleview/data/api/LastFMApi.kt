package com.frontmatic.scrobbleview.data.api

import com.frontmatic.scrobbleview.BuildConfig
import com.frontmatic.scrobbleview.data.model.FriendApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMApi {

    @GET(".")
    suspend fun getFriends(
        @Query("method") method: String = "user.getfriends",
        @Query("user") user: String = "vladbc",
//        @Query("api_key") api_key: String = BuildConfig.API_KEY,
//        @Query("format") format: String = "json",
        @Query("page") page: Int?
    ): FriendApiResponse
}