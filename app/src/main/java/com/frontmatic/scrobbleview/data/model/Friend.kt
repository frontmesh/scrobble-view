package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "friends")
data class Friend (
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val realname: String,
    val country: String,
    val url: String,
    val image: List<Image>
) {
    val largeImage: String
        get() = image.find { it.size == ImageSize.LARGE.value }?.url ?: ""
}
