package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "recent_tracks")
data class RecentTrack(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val url: String,
    val date : TrackDate?,
    val artist: TrackArtist,
    val streamable: Int,
    val image: List<Image>,
    val mbid: String,

) {
    val largeImage: String
        get() = image.find { it.size == ImageSize.LARGE.value }?.url ?: ""
}