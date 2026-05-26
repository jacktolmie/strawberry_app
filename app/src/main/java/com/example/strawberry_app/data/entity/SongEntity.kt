package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val length: Long
)
