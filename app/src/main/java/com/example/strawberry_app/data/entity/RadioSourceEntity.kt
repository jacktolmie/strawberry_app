package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "radio_source")
data class RadioSourceEntity(
    @PrimaryKey
    val sourceName: String,
    val sourceLogo: String
)