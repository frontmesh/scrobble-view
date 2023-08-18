package com.frontmatic.scrobbleview.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Streamable(
    @SerializedName("#text")
    val text: String,
    val fulltrack: String
)