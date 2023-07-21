package com.frontmatic.scrobbleview.data.model.response

import com.frontmatic.scrobbleview.data.model.Friend
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

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