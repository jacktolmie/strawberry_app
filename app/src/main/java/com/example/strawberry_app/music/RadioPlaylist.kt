package com.example.strawberry_app.music

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class RadioPlaylist(
    val format: String,
    val quality: String,
    @SerialName("stream_url")
    val streamUrl: String
)
