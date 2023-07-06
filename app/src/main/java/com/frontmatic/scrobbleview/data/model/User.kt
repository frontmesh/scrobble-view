package com.frontmatic.scrobbleview.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Registered(
    val unixtime: Long,
    @SerializedName("#text")
    val text: String
)

@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val age: Int,
    val subscriber: Int,
    val realname: String,
    val bootstrap: Int,
    @SerializedName("playcount")
    val playCount: Int,
    val playlists: Int,

    @SerializedName("artist_count")
    val artistCount: Int,
    
    @SerializedName("track_count")
    val trackCount: Int,

    val registered: Registered,

    val country: String,
    val gender: String,
    val type: String,
    val url: String,

    val image: List<Image>,

) {

    val largeImage: String
        get() = image.find { it.size == ImageSize.LARGE.value }?.url ?: ""
}