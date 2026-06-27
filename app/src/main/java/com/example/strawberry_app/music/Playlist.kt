package com.example.strawberry_app.music

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class Playlist(
    val id: Long,
    val favourite: Boolean,
    val name: String,
    val songs: List<SongInfo>
)
