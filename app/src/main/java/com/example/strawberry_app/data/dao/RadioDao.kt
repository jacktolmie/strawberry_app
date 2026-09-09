package com.example.strawberry_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.strawberry_app.data.entity.RadioStationEntity
import com.example.strawberry_app.data.entity.RadioStreamEntity
import kotlinx.coroutines.flow.Flow

data class StationWithStreams(
    val bitrate: Int,
    val clickCount: Int,
    val country: String,
    val description: String,
    val donate: String,
    val format: String,
    val genre: List<String>,
    val homepage: String,
    val id: String,
    val image: String,
    val language: String,
    val quality: String,
    val stationName: String,
    val streamName: String,
    val stationSource: String,
    val stationUrl: String,
    val streamUrl: String,
    val votes: Int
)

data class RadioFilter(
    val bitrate: Int,
    val country: String,
    val format: String,
    val language: String,
    val stationName: String,
    val streamName: String,
    val votes: Int
)

@Dao
interface RadioDao {

    @Query("SELECT COUNT(*) FROM radioStation")
    suspend fun getStationCount(): Int

    @Query("SELECT COUNT(*) FROM radio_stream")
    suspend fun getStreamCount(): Int

    @Query("DELETE FROM radioStation")
    suspend fun deleteAll()

    @Query("DELETE FROM radioStation WHERE stationSource = :source")
    suspend fun deleteBySource(source: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStation(entity: RadioStationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreams(entities: List<RadioStreamEntity>)

    @Query("SELECT bitrate, country, format, language, streamName, stationName, votes FROM radioStation")
    fun getAllFilterValues(): Flow<List<RadioFilter>>

    @Query("SELECT DISTINCT stationSource FROM radioStation")
    fun getStationSources(): Flow<List<String>>

    @Query("""
        SELECT  radioStation.id, radioStation.bitrate, radioStation.clickCount, 
                radioStation.country, radioStation.description, radioStation.donate, 
                radioStation.format, radioStation.genre, radioStation.homepage, radioStation.image, 
                radioStation.language, radioStation.streamName, radioStation.stationName, 
                radioStation.stationSource, radioStation.stationUrl, radioStation.votes, 
                radio_stream.streamUrl, radio_stream.format, radio_stream.quality
        FROM radioStation
        JOIN radio_stream ON radio_stream.stationId = radioStation.id
        WHERE radioStation.stationSource = :source
    """)
    fun observeStationsForSource(source: String): Flow<List<StationWithStreams>>
}
