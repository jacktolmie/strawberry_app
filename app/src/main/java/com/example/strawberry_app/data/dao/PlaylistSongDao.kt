package com.example.strawberry_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import com.example.strawberry_app.music.SongInfo
import kotlinx.coroutines.flow.Flow

data class SongWithPosition(
    val id: Int,
    val artist: String,
    val album: String,
    val length: Long,
    val position: Long,
    val title: String
)

@Dao
interface PlaylistSongDao {

    @Query("""
    SELECT song.id, song.title, song.artist, song.album, song.length, playlist_song.position
    FROM playlist_song
    JOIN song ON song.id = playlist_song.songId
    WHERE playlist_song.playlistId = :playlistId AND playlist_song.position = :position
""")
    fun observeSongAtPosition(playlistId: Int, position: Long): Flow<SongWithPosition?>

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

    @Query("DELETE FROM playlist_song WHERE playlistId = :playlistId AND songId IN (:songsId)")
    suspend fun deleteForPlaylist(playlistId: Long, songsId: List<Long>)
}