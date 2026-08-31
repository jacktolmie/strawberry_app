package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.strawberry_app.music.RadioPlaylist

@Entity(tableName = "radioStation")
data class RadioStationEntity(
    @PrimaryKey
    val id: String,
    val bitrate: Int,
    val clickCount: Int,
    val country: String,
    val description: String,
    val donate: String,
    val format: String,
    val genre: String,
    val homepage: String,
    val image: String,
    val name: String,
    val playlists: List<RadioPlaylist>,
    val stationSource: String,
    val stationUrl: String,
    val tags: List<String>,
    val votes: Int
)
