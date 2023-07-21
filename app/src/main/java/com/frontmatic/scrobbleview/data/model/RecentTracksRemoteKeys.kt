package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_tracks_remote_keys")
data class RecentTracksRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)
