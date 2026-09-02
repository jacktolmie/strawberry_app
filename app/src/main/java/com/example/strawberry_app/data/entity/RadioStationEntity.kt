package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val genre: List<String>,
    val homepage: String,
    val image: String,
    val language: String,
    val name: String,
    val stationSource: String,
    val stationUrl: String,
    val votes: Int
)
