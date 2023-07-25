package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.frontmatic.scrobbleview.data.api.RequestPeriod

@Entity(tableName = "top_tracks_remote_keys")
data class TopTracksRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val period: RequestPeriod,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)
