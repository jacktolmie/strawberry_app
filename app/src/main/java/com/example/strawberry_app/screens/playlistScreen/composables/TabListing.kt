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
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData


@Composable
fun TabListing(
    callbacks: PlaylistCallbacks,
    playlistScreenState: PlaylistScreenState,
    modifier: Modifier = Modifier
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val safeIndex = selectedTabIndex
        .coerceAtMost((playlistScreenState.playlistsData.playlists.size - 1).coerceAtLeast(0))

    LaunchedEffect(playlistScreenState.playlistsData.playlistState.currentPlaylist) {
        val playlist = playlistScreenState.playlistsData.playlists.firstOrNull {
            it.id == playlistScreenState.playlistsData.playlistState.currentPlaylist
        }
        val index = playlist?.let {
            playlistScreenState.playlistsData.playlists.indexOf(it)
        }?.takeIf { it >= 0 } ?: 0

        selectedTabIndex = index
    }

    val scrollState = rememberScrollState()
    var playlistId by remember { mutableLongStateOf(playlistScreenState.playlistsData.playlistState.currentPlaylist) }
    val onTabSelected: (Int, Long) -> Unit = {tabIndex, id ->
        selectedTabIndex = tabIndex
        playlistId = id
        callbacks.onPlaylistSelected(id)
        callbacks.clearSelectedSongs()
    }

    if ( playlistScreenState.playlistsData.playlists.size > 4) {
        ScrollableTabs(
            callbacks = callbacks,
            playlists = playlistScreenState.playlistsData.playlists,
            selectedTabIndex = safeIndex,
            onTabSelected = { tabIndex: Int, id: Long -> onTabSelected(tabIndex, id)
            },
            scrollState = scrollState
        )
    }else{
        StaticTabs(
            callbacks = callbacks,
            playlists = playlistScreenState.playlistsData.playlists,
            selectedTabIndex = safeIndex,
            onTabSelected = { tabIndex: Int, id: Long -> onTabSelected(tabIndex, id)
            }
        )
    }

    MakePlaylistMenu(
        callbacks = callbacks,
        playlistId = playlistId,
        playlistsData = playlistScreenState.playlistsData,
        playlistName = getPlaylistName(
            playlistsData = playlistScreenState.playlistsData,
            playlistId = playlistId
        ),
        selectedTabIndex = safeIndex,
        modifier = modifier
    )
}

fun getPlaylistName(
    playlistsData: PlaylistsData,
    playlistId: Long
): String {
    return playlistsData.playlists.firstOrNull{ it.id == playlistId}?.name ?: ""
}

@Preview
@Composable
fun TabListingPreview(){
    TabListing(
        callbacks = PlaylistCallbacks(),
        playlistScreenState = PlaylistScreenState()
    )
}