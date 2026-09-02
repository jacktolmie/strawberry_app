package com.example.strawberry_app.screens.radioScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.data.dao.StationWithStreams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(
    private val radioRepository: RadioRepository
): ViewModel() {

    fun getStationsBySource(source: String) = radioRepository.getStations(source)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun sortStationsBy(source: String, sortBy: RadioStation, ascending: Boolean): List<StationWithStreams>{
        val stations = getStationsBySource(source).value
        return when (sortBy) {
            RadioStation.BITRATE ->  {
                if (ascending) stations.sortedBy { it.bitrate } else stations.sortedByDescending { it.bitrate }
            }
            RadioStation.CLICKCOUNT -> {
                if (ascending) stations.sortedBy { it.clickCount } else stations.sortedByDescending { it.clickCount }
            }
            RadioStation.CODEC -> {
                if (ascending) stations.sortedBy { it.format } else stations.sortedByDescending { it.format }
            }
            RadioStation.COUNTRY -> {
                if (ascending) stations.sortedBy { it.country } else stations.sortedByDescending { it.country }
            }
            RadioStation.LANGUAGE -> {
                if (ascending) stations.sortedBy { it.language } else stations.sortedByDescending { it.language }
            }
            RadioStation.NAME -> {
                if (ascending) stations.sortedBy { it.name } else stations.sortedByDescending { it.name }
            }
            RadioStation.TAGS -> { stations } // make function call to sort by tags
            RadioStation.VOTES -> {
                if (ascending) stations.sortedBy { it.votes } else stations.sortedByDescending { it.votes }
            }
        }
    }

    fun showStationByTags() {
//        val stations = getStationsBySource()
    }

}