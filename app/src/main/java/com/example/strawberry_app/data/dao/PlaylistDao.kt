package com.example.strawberry_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.strawberry_app.data.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Query("DELETE FROM playlist")
    suspend fun deleteAll()

    @Query("DELETE FROM playlist WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT id from playlist WHERE id = :id")
    suspend fun getPlaylistId(id: Long): Long?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(playlist: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(playlists: List<PlaylistEntity>)

    @Query("SELECT * from playlist ORDER BY name")
    fun observeAll(): Flow<List<PlaylistEntity>>

    @Query("UPDATE playlist SET favourite = :favourite WHERE id = :id")
    suspend fun updateFavourite(id: Long, favourite: Boolean)

    @Query("UPDATE playlist SET name = :name WHERE id = :id")
    suspend fun updateName(id: Long, name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlaylist(playlist: PlaylistEntity)
}