package com.frontmatic.scrobbleview.data.model

import android.util.Base64
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Date

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