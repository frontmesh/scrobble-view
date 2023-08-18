package com.frontmatic.scrobbleview.data.model

data class TrackInfoAlbum(
    val artist: String,
    val image: List<Image>,
    val mbid: String,
    val title: String,
    val url: String
)