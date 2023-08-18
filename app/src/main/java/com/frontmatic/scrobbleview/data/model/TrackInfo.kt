package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "track_info")
data class TrackInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val album: TrackInfoAlbum,
    val artist: Artist,
    val duration: String,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val streamable: Streamable,
    val toptags: List<TrackTag>,
    val url: String,
    val wiki: Wiki
)