package com.frontmatic.scrobbleview.data.api

import com.frontmatic.scrobbleview.data.model.response.FriendApiResponse
import com.frontmatic.scrobbleview.data.model.UserApiResponse
import com.frontmatic.scrobbleview.data.model.response.RecentTracksApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

enum class RequestPeriod(val value: String) {
    OVERALL("overall"),
    SEVEN_DAY("7day"),
    ONE_MONTH("1month"),
    THREE_MONTH("3month"),
    SIX_MONTH("6month"),
    TWELVE_MONTH("12month");

    override fun toString(): String {
        return when (this) {
            OVERALL -> "Overall"
            SEVEN_DAY -> "Seven Days"
            ONE_MONTH -> "One Month"
            THREE_MONTH -> "Three Months"
            SIX_MONTH -> "Six Months"
            TWELVE_MONTH -> "Twelve Months"
        }
    }
}


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

    @GET(".")
    suspend fun getTopTracks(
        @Query("method") method: String = "user.gettoptracks",
        @Query("user") user: String,
        @Query("page") page: Int?,
        @Query("period") period : RequestPeriod?
    ): RecentTracksApiResponse
}