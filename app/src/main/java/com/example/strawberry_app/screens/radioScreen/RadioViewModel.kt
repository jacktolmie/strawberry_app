package com.example.strawberry_app.screens.radioScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.data.dao.StationWithStreams
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class RadioFilterState(
    val bitrates: List<Int> = emptyList(),
    val countries: List<String> = emptyList(),
    val formats: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val streamNames: List<String> = emptyList(),
    val stationNames: List<String> = emptyList(),
    val votes: List<Int> = emptyList()
)

data class RadioScreenState(
    val albumArtCollection: Map<String, File?> = emptyMap(),
    val radioState: RadioState = RadioState(),
    val stations: List<StationWithStreams> = emptyList(),
    val stationSource: List<String> = emptyList(),
    val sortedStations: List<StationWithStreams> = emptyList()
)
@HiltViewModel
class RadioViewModel @Inject constructor(
    private val radioRepository: RadioRepository
): ViewModel() {

    val albumArtCollection = radioRepository.artAlbumCollection
    val radioState = radioRepository.radioState

    private val _stations = MutableStateFlow<List<StationWithStreams>>(emptyList())
    val stations = _stations.asStateFlow()

    private val _stationSources = MutableStateFlow<List<String>>(emptyList())
    val stationSources = _stationSources.asStateFlow()

    private val _sortedStations = MutableStateFlow<List<StationWithStreams>>(emptyList())
    val sortedStations = _sortedStations.asStateFlow()

    val radioScreenState = combine(
        albumArtCollection, radioState, stations, stationSources, sortedStations
    ){
        art, radioState, stations, sources, sorted ->
        RadioScreenState(art, radioState, stations, sources, sorted)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RadioScreenState()
    )

    // Types of station data (bitrate, country etc).
//    private val _bitrates = MutableStateFlow<List<Int>>(emptyList())
//    val bitrates = _bitrates.asStateFlow()
//
//    private val _countries = MutableStateFlow<List<String>>(emptyList())
//    val countries = _countries.asStateFlow()
//
//    private val _formats = MutableStateFlow<List<String>>(emptyList())
//    val format = _formats.asStateFlow()
//
    private val _genres = MutableStateFlow<List<String>>(emptyList())
    val genres = _genres.asStateFlow()
//
//    private val _languages = MutableStateFlow<List<String>>(emptyList())
//    val language = _languages.asStateFlow()
//
//    private val _names = MutableStateFlow<List<String>>(emptyList())
//    val names = _names.asStateFlow()
//
//    private val _votes = MutableStateFlow<List<Int>>(emptyList())
//    val votes = _votes.asStateFlow()

    private val _filterState = MutableStateFlow(RadioFilterState())
    val filterState = _filterState.asStateFlow()

    init {
        loadStations()
    }

    fun loadStations() {
        viewModelScope.launch {
            radioRepository.getStationSources().collect { sources ->
                _stationSources.value = sources
                val allStations = mutableListOf<StationWithStreams>()
                sources.forEach { source ->
                    val stations = radioRepository.getStationsWithStreams(source).first()
                    allStations.addAll(stations)
                }
                _stations.value = allStations
            }
        }
    }

    fun getAlbumArtFile(coverArt: String): File? = radioRepository.getAlbumArtFile(coverArt)

    fun getStationsBySource(source: String) = _stations.value.filter { it.stationSource == source }

    fun sortStationsByType(source: String, sortBy: RadioStation, ascending: Boolean){
        val stations = getStationsBySource(source)
        _sortedStations.value =  when (sortBy) {
            RadioStation.BITRATE ->  { sortStations(stations, ascending) { it.bitrate} }
            RadioStation.CLICKCOUNT -> { sortStations(stations, ascending) { it.clickCount} }
            RadioStation.COUNTRY -> { sortStations(stations, ascending) { it.country} }
            RadioStation.FORMAT -> { sortStations(stations, ascending) { it.format} }
            RadioStation.GENRE -> { sortStations(stations, ascending) { it.genre.firstOrNull()} }
            RadioStation.LANGUAGE -> { sortStations(stations, ascending) { it.language} }
            RadioStation.STATIONNAME -> { sortStations(stations, ascending) { it.stationName} }
            RadioStation.STREAMNAME -> { sortStations(stations, ascending) { it.streamName} }
            RadioStation.VOTES -> { sortStations(stations, ascending) { it.votes} }
        }
    }

    fun loadFilterValues() {
        viewModelScope.launch {
            radioRepository.getAllFilterValues().collect { stations ->
                _filterState.value = RadioFilterState(
                    bitrates = stations.map { it.bitrate }.distinct().sorted(),
                    countries = stations.map { it.country }.distinct().sorted(),
                    formats = stations.map { it.format }.distinct().sorted(),
                    genres = genres.value,
                    languages = stations.map { it.language }.distinct().sorted(),
                    streamNames = stations.map { it.streamName}.distinct().sorted(),
                    stationNames = stations.map { it.stationName }.distinct().sorted(),
                    votes = stations.map { it.votes }.distinct().sorted()
                )
            }
        }
    }

    fun <T : Comparable<T>> sortStations(
        stations: List<StationWithStreams>,
        ascending: Boolean,
        selector: (StationWithStreams) -> T?
    ): List<StationWithStreams> {
        return if (ascending) stations.sortedBy(selector) else stations.sortedByDescending(selector)
    }

    fun getStationGenre(source: String, ascending: Boolean) {
        val stations = getStationsBySource(source)
        val genres = stations.flatMap { it.genre }.distinct()
        _genres.value = if (ascending) genres.sorted() else genres.sortedDescending()
    }

    fun createPlaylist(streams: List<StationWithStreams>) {
        if (streams.isEmpty()) return
        radioRepository.sendCommand(
            OutgoingMessage.SendStations(
                source = streams.first().stationSource,
                streams = streams.map{
                    StreamInfo(
                        name = it.streamName,
                        url = it.stationUrl
                    )
                }
            )
        )
    }
}