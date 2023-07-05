package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friend_remote_keys")
data class FriendRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long = System.currentTimeMillis()
)