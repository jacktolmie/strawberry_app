package com.example.strawberry_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.data.dao.RadioDao
import com.example.strawberry_app.data.dao.SongDao
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import com.example.strawberry_app.data.entity.RadioSourceEntity
import com.example.strawberry_app.data.entity.RadioStationEntity
import com.example.strawberry_app.data.entity.RadioStreamEntity
import com.example.strawberry_app.data.entity.SongEntity
import com.example.strawberry_app.data.entity.StringListConverters

@Database(
    entities =
        [
            PlaylistEntity::class,
            SongEntity::class,
            PlaylistSongEntity::class,
            RadioSourceEntity::class,
            RadioStationEntity::class,
            RadioStreamEntity::class
        ],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListConverters::class)

abstract class AppDatabase: RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun songDao(): SongDao
    abstract fun playlistSongDao(): PlaylistSongDao
    abstract fun radioStationDao(): RadioDao
}