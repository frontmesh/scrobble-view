package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

enum class ImageSize(val value: String) {
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large"),
    EXTRALARGE("extralarge")
}


@Serializable
data class Image(
    val size: String,

    @SerializedName("#text")
    val url: String
)

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
