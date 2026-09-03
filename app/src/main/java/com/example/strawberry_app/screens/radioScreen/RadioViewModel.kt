package com.example.strawberry_app.screens.radioScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.data.dao.StationWithStreams
import com.example.strawberry_app.screens.repositories.AlbumArtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    private val radioRepository: RadioRepository
): ViewModel() {

    val albumArtCollection = radioRepository.artAlbumCollection
    val radioState = radioRepository.radioState

    fun getStationsBySource(source: String) = radioRepository.getStations(source)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun getStations(source: String, sortBy: RadioStation, ascending: Boolean): List<StationWithStreams>{
        val stations = getStationsBySource(source).value
        return when (sortBy) {
            RadioStation.BITRATE ->  { sortStations(stations, ascending) { it.bitrate} }
            RadioStation.CLICKCOUNT -> { sortStations(stations, ascending) { it.clickCount} }
            RadioStation.FORMAT -> { sortStations(stations, ascending) { it.format} }
            RadioStation.COUNTRY -> { sortStations(stations, ascending) { it.country} }
            RadioStation.GENRE -> { sortStations(stations, ascending) { it.genre.first()} }
            RadioStation.LANGUAGE -> { sortStations(stations, ascending) { it.language} }
            RadioStation.NAME -> { sortStations(stations, ascending) { it.name} }
            RadioStation.VOTES -> { sortStations(stations, ascending) { it.votes} }
        }
    }

    fun <T : Comparable<T>> sortStations(
        stations: List<StationWithStreams>,
        ascending: Boolean,
        selector: (StationWithStreams) -> T?
    ): List<StationWithStreams> {
        return if (ascending) stations.sortedBy(selector) else stations.sortedByDescending(selector)
    }

    fun sortStationsByGenre(source: String, ascending: Boolean): List<String> {
        val stations = getStationsBySource(source).value
        val genres = stations.flatMap { it.genre }.distinct()
        return if (ascending) genres.sorted() else genres.sortedDescending()
    }

}