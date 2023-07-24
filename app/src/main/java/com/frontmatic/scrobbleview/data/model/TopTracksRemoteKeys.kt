package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_tracks_remote_keys")
data class TopTracksRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)
