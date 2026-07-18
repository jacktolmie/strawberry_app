package com.example.strawberry_app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "playlist_song",
    primaryKeys = ["playlistId", "position"],
    foreignKeys = [
        ForeignKey(
            entity = PlaylistEntity::class,
            parentColumns = ["id"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SongEntity::class,
            parentColumns = ["url"],
            childColumns = ["songUrl"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("songUrl")]
)
data class PlaylistSongEntity(
    val playlistId: Long,
    val songUrl: String,
    val position: Int
)
