package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "radioStation")
data class RadioStationEntity(
    @PrimaryKey
    val id: String,
    val description: String,
    val donate: String,
    val genre: String,
    val image: String,
    val name: String,
    val stationSource: String,
    val stationUrl: String
)
