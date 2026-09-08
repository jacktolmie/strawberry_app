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
import com.example.strawberry_app.screens.repositories.AlbumArtRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

data class RadioState(
    val activeStation: String = "",
    val activeStream: String = "",
)

@Singleton
class RadioRepository @Inject constructor(
    private val albumArtRepository: AlbumArtRepository,
    private val db: AppDatabase,
    private val networkManager: NetworkManager,
    private val radioDao: RadioDao,

    @param:ApplicationScope
    private val scope: CoroutineScope
) {
    private val _radioState = MutableStateFlow(RadioState())
    val radioState = _radioState.asStateFlow()

    val artAlbumCollection = albumArtRepository.artAlbumCollection

    fun sendCommand(command: OutgoingMessage){
        scope.launch { networkManager.sendCommand(command) }
    }

    // Radio database queries.
    suspend fun deleteRadioStations() { radioDao.deleteAll() }

    // Get a list of information by type.
    fun getAllFilterValues() = radioDao.getAllFilterValues()
     fun getStationSources() = radioDao.getStationSources()

    // Get a list of station streams.
    fun getStationsWithStreams(source: String) = radioDao.observeStationsForSource(source)

    suspend fun makeAllStations(radioStations: List<RadioStation>, stationSource: String){
        db.withTransaction {
            if (stationSource != RadioSource.RADIOBROWSER.source) {
                radioDao.deleteBySource(stationSource)
            }

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
                        language = station.language,
                        name = station.name,
                        stationSource = station.stationSource,
                        stationUrl = station.stationUrl,
                        votes = station.votes
                    )
                )

                if (station.playlists.isEmpty() && station.stationUrl.isNotEmpty()) {
                    val streams = listOf(RadioStreamEntity(
                        stationId = station.id,
                        format = station.format,
                        quality = "",
                        streamUrl = station.stationUrl
                    ))
                    radioDao.insertStreams(streams)
                } else {
                    val streams = station.playlists.map { playlist ->
                        RadioStreamEntity(
                            stationId = station.id,
                            format = playlist.format,
                            quality = playlist.quality,
                            streamUrl = playlist.streamUrl
                        )
                    }
                    radioDao.insertStreams(streams)
                }
            }
        }
        println("RadioStations: ${radioDao.getStationCount()} and streams ${radioDao.getStreamCount()}")
    }

    fun getAlbumArtFile(name: String): File? = albumArtRepository.getAlbumArtFile(name )

}