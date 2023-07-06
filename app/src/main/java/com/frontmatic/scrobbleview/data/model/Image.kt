package com.frontmatic.scrobbleview.data.model

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
