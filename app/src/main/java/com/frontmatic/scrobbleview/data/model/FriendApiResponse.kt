package com.frontmatic.scrobbleview.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Attr(
    val user: String,
    val page: Int,
    val total: Int,
    val perPage: Int,
    val totalPages: Int,
)

@Serializable
data class FriendApiResponse(
    val friends: FriendResponse
)

@Serializable
data class FriendResponse(
    @SerializedName("@attr")
    val attr: Attr,
    val user: List<Friend> = emptyList(),
)