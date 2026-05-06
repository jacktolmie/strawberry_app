package com.example.strawberry_app.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val id: Int,
    val artist: String,
    val album: String,
    val title: String,
    val length: Long
)
