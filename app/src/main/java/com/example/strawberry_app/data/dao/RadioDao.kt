package com.example.strawberry_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.strawberry_app.data.entity.RadioStationEntity
import com.example.strawberry_app.data.entity.RadioStreamEntity
import kotlinx.coroutines.flow.Flow

data class StationWithStreams(
    val id: String,
    val name: String,
    val image: String,
    val genre: String,
    val description: String,
    val stationSource: String,
    val url: String,
    val format: String,
    val quality: String

)

@Dao
interface RadioDao {
    @Query("DELETE FROM radioStation")
    suspend fun deleteAll()

    @Query("DELETE FROM radioStation WHERE stationSource = :source")
    suspend fun deleteBySource(source: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStation(entity: RadioStationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreams(entities: List<RadioStreamEntity>)

    @Query("""
        SELECT radioStation.name, radioStation.id, radioStation.image,
               radioStation.genre, radioStation.description, radioStation.stationSource,
               radio_stream.url, radio_stream.format, radio_stream.quality
        FROM radioStation
        JOIN radio_stream ON radio_stream.stationId = radioStation.id
        WHERE radioStation.stationSource = :source
    """)
    fun observeStationsForSource(source: String): Flow<List<StationWithStreams>>


}
