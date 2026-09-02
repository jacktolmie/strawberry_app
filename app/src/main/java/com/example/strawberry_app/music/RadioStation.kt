package com.example.strawberry_app.music

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
data class RadioStation(
    val bitrate: Int,
    @SerialName("click_count")
    val clickCount: Int,
    val country: String,
    val description: String,
    val donate: String,
    val format: String,
    val genre: List<String>,
    val homepage: String,
    val id: String,
    val image: String,
    val language: String,
    val name: String,
    val playlists: List<RadioPlaylist>,
    @SerialName("station_url")
    val stationUrl: String,
    val votes: Int
)
