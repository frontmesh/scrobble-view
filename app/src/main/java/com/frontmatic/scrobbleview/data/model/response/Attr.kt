package com.frontmatic.scrobbleview.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Attr(
    val user: String,
    val page: Int,
    val total: Int,
    val perPage: Int,
    val totalPages: Int,
)