package com.example.strawberry_app.music

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SongInfo(
    val artist: String = "",
    val album: String = "",
    @SerialName("cover_image")
    val coverImage: String = "",
    val id: Long = -1L,
    val length: Long = -1L,
    val playlistId: Long = 1L,
    val position: Long = -1L,
    val title: String = "",
    @SerialName("song_url")
    val url: String = ""
)