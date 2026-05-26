package com.example.strawberry_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import kotlinx.coroutines.flow.Flow

data class SongWithPosition(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val length: Long,
    val position: Int
)

@Dao
interface PlaylistSongDao {

    @Query("""
        SELECT song.id, song.title, song.artist, song.album, song.length, playlist_song.position
        FROM playlist_song
        JOIN song ON song.id = playlist_song.songId
        WHERE playlist_song.playlistId = :playlistId
    """)
    fun observeSongsForPlaylist(playlistId: Long): Flow<List<SongWithPosition>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PlaylistSongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<PlaylistSongEntity>)

    @Query("DELETE FROM playlist_song WHERE playlistId = :playlistId")
    suspend fun deleteForPlaylist(playlistId: Long)
}