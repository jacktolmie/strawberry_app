package com.example.strawberry_app.screens.playlistScreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.composables.TextBox
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.screens.playlistScreen.composables.CurrentPlaylist
import com.example.strawberry_app.screens.playlistScreen.composables.TabListing

@Composable
fun PlaylistScreen(
    callbacks: PlaylistCallbacks,
    isPortrait: Boolean,
    playlistScreenState: PlaylistScreenState,
    deviceType: DeviceTypesBreakdown,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxSize()
        .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        // Create the scrollable tab
        if (playlistScreenState.playlistsData.playlists.isNotEmpty()) {
            TabListing(
                callbacks = callbacks,
                isPortrait = isPortrait,
                playlistScreenState = playlistScreenState,
                deviceType = deviceType
            )
        }
        else { // If no playlist or disconnected, show this.
            TextBox(
                color = MaterialTheme.colorScheme.onSurface,
                textRes = R.string.playlist_no_playlists,
                textStyle = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
        // Create the playlists for each tab.
        if (playlistScreenState.playlistsData.playlistSongs.isNotEmpty()) {
            CurrentPlaylist(
                albumArtCollection = playlistScreenState.albumArtCollection,
                callbacks = callbacks,
                playlist = playlistScreenState.playlistsData.playlistSongs,
                playlistScreenState = playlistScreenState
            )
        }
    }
}

@Composable
@Preview
fun PlaylistPreview(){
    PlaylistScreen(
        callbacks = PlaylistCallbacks(),
        isPortrait = true,
        playlistScreenState = PlaylistScreenState(),
        deviceType = DeviceTypesBreakdown.PHONE_PORTRAIT,
        modifier = Modifier.background(Color.White)
    )
}