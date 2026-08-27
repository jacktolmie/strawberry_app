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

            radioStations.forEach { station ->
                radioDao.insertStation(
                    RadioStationEntity(
                        id =  station.id,
                        description = station.description,
                        genre = station.genre,
                        image = station.image,
                        name = station.name,
                        stationSource = stationSource
                    )
                )

                val streams = station.playlists.map { stream ->
                    RadioStreamEntity(
                        format = stream.format,
                        quality = stream.quality,
                        url =  stream.url,
                        stationId = station.id
                    )
                }
            }
        }
    }
}