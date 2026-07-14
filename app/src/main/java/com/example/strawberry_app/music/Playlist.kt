package com.example.strawberry_app.music

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class Playlist(
    val id: Long,
    val favourite: Boolean,
    val name: String,
    val songs: List<SongInfo>,
    @SerialName("playlist_size")
    val playlistSize: Long,
    @SerialName("playlist_length")
    val playlistLength: Long
)