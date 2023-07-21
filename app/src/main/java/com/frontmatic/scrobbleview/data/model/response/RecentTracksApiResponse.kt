package com.frontmatic.scrobbleview.data.model.response

import com.frontmatic.scrobbleview.data.model.RecentTrack
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RecentTracksApiResponse(
    val recenttracks: RecentTracksResponse,
)

data class RecentTracksResponse(
    val track: List<RecentTrack>,
    @SerializedName("@attr")
    val attr: Attr,
)