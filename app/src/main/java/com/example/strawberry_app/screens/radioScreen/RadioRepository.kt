package com.example.strawberry_app.screens.radioScreen

import androidx.room.withTransaction
import com.example.strawberry_app.data.dao.RadioDao
import com.example.strawberry_app.data.db.AppDatabase
import com.example.strawberry_app.data.entity.RadioStationEntity
import com.example.strawberry_app.data.entity.RadioStreamEntity
import com.example.strawberry_app.music.RadioStation
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.OutgoingMessage
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RadioState(
    val activeStation: String = "",
    val activeStream: String = "",
)

@Singleton
class RadioRepository @Inject constructor(
    private val db: AppDatabase,
    private val networkManager: NetworkManager,
    private val radioDao: RadioDao,

    @param:ApplicationScope
    private val scope: CoroutineScope
){
    private val _radioState = MutableStateFlow(RadioState())
    val radioState = _radioState.asStateFlow()

    fun sendCommand(command: OutgoingMessage){
        scope.launch { networkManager.sendCommand(command) }
    }

    // Radio database changes.
    suspend fun deleteRadioStations() { radioDao.deleteAll() }

    suspend fun makeAllStations(radioStations: List<RadioStation>, stationSource: String){
        db.withTransaction {
            radioDao.deleteAll()

            // Insert radio streams, then stations.
            radioStations.forEach { station ->
                radioDao.insertStation(
                    RadioStationEntity(
                        id =  station.id,
                        bitrate = station.bitrate,
                        clickCount = station.clickCount,
                        country = station.country,
                        description = station.description,
                        donate = station.donate,
                        format = station.format,
                        genre = station.genre,
                        homepage = station.homepage,
                        image = station.image,
                        name = station.name,
                        playlists = station.playlists,
                        stationSource = stationSource,
                        stationUrl = station.stationUrl,
                        tags = station.tags,
                        votes = station.votes
                    )
                )

                // Insert stations from each stream.
                val streams = station.playlists.map { stream ->
                    RadioStreamEntity(
                        format = stream.format,
                        quality = stream.quality,
                        streamUrl =  stream.streamUrl,
                        stationId = station.id
                    )
                }

                radioDao.insertStreams(streams)
            }
        }
    }
}