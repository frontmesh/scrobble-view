package com.frontmatic.scrobbleview.data.model.response

import com.frontmatic.scrobbleview.data.model.TopTrack
import com.google.gson.annotations.SerializedName

data class TopTracksApiResponse(
    val toptracks: TopTracksResponse,
)

data class TopTracksResponse(
    val track: List<TopTrack>,

    @SerializedName("@attr")
    val attr: Attr,
)
