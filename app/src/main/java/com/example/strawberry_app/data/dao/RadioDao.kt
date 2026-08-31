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
    val donate: String,
    val stationSource: String,
    val stationUrl: String,
    val streamUrl: String,
    val format: String,
    val quality: String
// Add RadioStationEntity variables to this???
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
        SELECT  radioStation.id, radioStation.bitrate, radioStation.clickCount, 
                radioStation.country, radioStation.description, radioStation.donate, 
                radioStation.format, radioStation.genre, radioStation.homepage, radioStation.image, 
                radioStation.name, radioStation.stationSource, radioStation.stationUrl,
                radioStation.tags, radioStation.votes,
               radio_stream.streamUrl, radio_stream.format, radio_stream.quality
        FROM radioStation
        JOIN radio_stream ON radio_stream.stationId = radioStation.id
        WHERE radioStation.stationSource = :source
    """)
    fun observeStationsForSource(source: String): Flow<List<StationWithStreams>>


}
