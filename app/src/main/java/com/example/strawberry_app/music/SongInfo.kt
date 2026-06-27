package com.example.strawberry_app.music

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class SongInfo(
    val id: Long = -1L,
    val artist: String = "",
    val album: String = "",
    val title: String = "",
    val length: Long = -1L
)
