package com.frontmatic.scrobbleview.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    val name: String,
    val url: String,
    val date : TrackDate,
    val artist: TrackArtist,
    val streamable: Int,
    val image: List<Image>,
    val mbid: String,

)

@Serializable
data class TrackArtist(
    @SerializedName("#text")
    val name: String,
    val mbid: String,
)

@Serializable
data class TrackAlbum(
    @SerializedName("#text")
    val name: String,
    val mbid: String,
)

@Serializable
data class TrackDate(
    val uts: Long,
    @SerializedName("#text")
    val text: String,
)