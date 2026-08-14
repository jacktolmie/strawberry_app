package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.ScrollState
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
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData

data class TabScreenState(
    val onTabSelected: (Int, Long) -> Unit,
    val playlists: List<PlaylistEntity>,
    val deviceType: DeviceTypesBreakdown,
    val scrollState: ScrollState,
    val selectedTabIndex: Int
)

@Composable
fun TabListing(
    callbacks: PlaylistCallbacks,
    isPortrait: Boolean,
    playlistScreenState: PlaylistScreenState,
    deviceType: DeviceTypesBreakdown,
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

    var playlistId by remember { mutableLongStateOf(playlistScreenState.playlistsData.playlistState.currentPlaylist) }
    val onTabSelected: (Int, Long) -> Unit = {tabIndex, id ->
        selectedTabIndex = tabIndex
        playlistId = id
        callbacks.onPlaylistSelected(id)
        callbacks.clearSelectedSongs()
    }

    val scrollState = rememberScrollState()

    val tabScreenState = TabScreenState(
        onTabSelected = { tabIndex: Int, id: Long -> onTabSelected(tabIndex, id) },
        playlists = playlistScreenState.playlistsData.playlists,
        deviceType = deviceType,
        scrollState = scrollState,
        selectedTabIndex = selectedTabIndex
    )

    if ( playlistScreenState.playlistsData.playlists.size > 4) {
        ScrollableTabs(
            callbacks = callbacks,
            isPortrait = isPortrait,
            tabScreenState = tabScreenState
        )
    }else{
        StaticTabs(
            callbacks = callbacks,
            isPortrait = isPortrait,
           tabScreenState = tabScreenState
        )
    }

    MakePlaylistMenu(
        callbacks = callbacks,
        isPortrait = isPortrait,
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
        isPortrait = true,
        deviceType = DeviceTypesBreakdown.PHONE_PORTRAIT,
        playlistScreenState = PlaylistScreenState()
    )
}