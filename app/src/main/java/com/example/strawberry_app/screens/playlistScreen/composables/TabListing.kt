package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData


@Composable
fun TabListing(
    callbacks: PlaylistCallbacks,
    playlistsData: PlaylistsData,
    modifier: Modifier = Modifier
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(playlistsData.playlistState.currentPlaylist) {

        val index = playlistsData.playlists.indexOfFirst {
            it.id == playlistsData.playlistState.currentPlaylist
        }.takeIf { it >= 0 } ?: 0

        selectedTabIndex = index
    }
    val scrollState = rememberScrollState()
    var playlistId by remember { mutableLongStateOf(playlistsData.playlistState.currentPlaylist) }

    if ( playlistsData.playlists.size > 4) {
        ScrollableTabs(
            callbacks = callbacks,
            playlistsData = playlistsData,
            selectedTabIndex = selectedTabIndex,
            playlistId = playlistId,
            onTabSelected = { tabIndex: Int, id: Long ->
                selectedTabIndex = tabIndex
                playlistId = id
            },
            scrollState = scrollState
        )
    }else{
        StaticTabs(
            callbacks = callbacks,
            playlistsData = playlistsData,
            playlistId = playlistId,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { tabIndex: Int, id: Long ->
                selectedTabIndex = tabIndex
                playlistId = id
            }
        )
    }

    MakePlaylistMenu(
        callbacks = callbacks,
        playlistId = playlistId,
        playlistsData = playlistsData,
        selectedTabIndex = selectedTabIndex,
        modifier = modifier)
}