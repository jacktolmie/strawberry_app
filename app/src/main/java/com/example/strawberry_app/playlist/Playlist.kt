package com.example.strawberry_app.playlist

import kotlinx.serialization.Serializable

@Serializable
data class Playlist(
    val id: Int,
    val name: String,
    val songs: List<Song>
)
