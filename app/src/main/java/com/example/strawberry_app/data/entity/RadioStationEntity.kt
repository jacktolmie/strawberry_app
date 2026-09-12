package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "radioStation",
    foreignKeys = [
        ForeignKey(
            entity = RadioSourceEntity::class,
            parentColumns = ["sourceName"],
            childColumns = ["sourceName"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("sourceName")]
)
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
    val language: String,
    val sourceName: String,
    val streamName: String,
    val stationIcon: String,
    val stationName: String,
    val stationUrl: String,
    val votes: Int
)
