package com.example.strawberry_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import kotlinx.coroutines.flow.Flow

data class SongWithPosition(
    val id: Long,
    val url: String,
    val artist: String,
    val album: String,
    val coverImage: String,
    val length: Long,
    val position: Long,
    val title: String
)

@Dao
interface PlaylistSongDao {

    @Query("DELETE FROM playlist_song WHERE playlistId = :playlistId")
    suspend fun delete(playlistId: Long)

    @Query("""
    SELECT song.id, song.url, song.coverImage, song.title, song.artist, song.album, song.length, playlist_song.position
    FROM playlist_song
    JOIN song ON song.url = playlist_song.songUrl
    WHERE playlist_song.playlistId = :playlistId AND playlist_song.position = :position
""")
    fun observeSongAtPosition(playlistId: Long, position: Long): Flow<SongWithPosition?>

    @Query("""
        SELECT song.id, song.url, song.coverImage, song.title, song.artist, song.album, song.length, playlist_song.position
        FROM playlist_song
        JOIN song ON song.url = playlist_song.songUrl
        WHERE playlist_song.playlistId = :playlistId
        ORDER BY playlist_song.position
    """)
    fun observeSongsForPlaylist(playlistId: Long): Flow<List<SongWithPosition>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PlaylistSongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<PlaylistSongEntity>)

    @Query("DELETE FROM playlist_song WHERE playlistId = :playlistId AND songUrl IN (:songsId)")
    suspend fun deleteForPlaylist(playlistId: Long, songsId: List<String>)
}