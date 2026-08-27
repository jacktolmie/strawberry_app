package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "radio_stream",
    primaryKeys = ["stationId", "url"],
    foreignKeys = [
        ForeignKey(
            entity = RadioStationEntity::class,
            parentColumns = ["id"],
            childColumns = ["stationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("stationId")]
)
data class RadioStreamEntity(
    val stationId: String,
    val format: String,
    val quality: String,
    val url: String
)
