package com.example.strawberry_app.screens.radioScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.data.dao.StationWithStreams
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import java.io.File

data class RadioCallbacks(
    val sortStationsByType: (
            source: String,
            sortBy: RadioStation,
            ascending: Boolean
            ) -> Unit = {_,_,_->},
    val sortStationsByGenre: (
            source: String,
            ascending: Boolean
            ) -> Unit = {_,_->},
    val getAlbumArtFile: (coverArt: String) -> File? = {null},
    val loadFilterValues: () -> Unit = {},
    val createPlaylist: (streams: List<StationWithStreams>) -> Unit = {}

)

@Composable
fun RadioRoute(
    isPortrait: Boolean,
    deviceType: DeviceTypesBreakdown,
    modifier: Modifier = Modifier,
    radioViewModel: RadioViewModel = hiltViewModel()
){
    val radioScreenState by radioViewModel.radioScreenState.collectAsStateWithLifecycle()
    val filteredRadioData by radioViewModel.filterState.collectAsStateWithLifecycle()

    val callbacks = RadioCallbacks(
        sortStationsByType = radioViewModel::sortStationsByType,
        sortStationsByGenre = radioViewModel::getStationGenre,
        getAlbumArtFile = radioViewModel::getAlbumArtFile,
        loadFilterValues = radioViewModel::loadFilterValues,
        createPlaylist = radioViewModel::createPlaylist,

    )
}