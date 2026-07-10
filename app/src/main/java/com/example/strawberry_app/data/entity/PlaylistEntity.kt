package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "playlist")
data class PlaylistEntity(
    @PrimaryKey val id: Long,
    val favourite: Boolean,
    val name: String,
    val playlistLength: Long,
    val playlistSize: Long
)