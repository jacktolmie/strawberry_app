package com.example.strawberry_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.strawberry_app.data.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(song: SongEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(songs: List<SongEntity>)

    @Query("DELETE FROM song WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM song WHERE id NOT IN (SELECT songId FROM playlist_song)")
    suspend fun deleteOrphans()

    @Query("SELECT * FROM song WHERE id = :id")
    fun observeById(id: Long): Flow<SongEntity?>
}