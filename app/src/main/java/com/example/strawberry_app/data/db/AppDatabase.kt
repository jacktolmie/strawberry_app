package com.example.strawberry_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.data.dao.SongDao
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import com.example.strawberry_app.data.entity.SongEntity

@Database(
    entities = [PlaylistEntity::class, SongEntity::class, PlaylistSongEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun songDao(): SongDao
    abstract fun playlistSongDao(): PlaylistSongDao
}