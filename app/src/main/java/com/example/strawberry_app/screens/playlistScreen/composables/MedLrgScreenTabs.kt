package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState

@Composable
fun MedLrgScreenTabs(
    callbacks: PlaylistCallbacks,
    playlistScreenState: PlaylistScreenState,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)
    ){
        // Create the scrollable tab
        if (playlistScreenState.playlistsData.playlists.isNotEmpty()) {
            TabListing(callbacks, playlistScreenState)
        }
        // Create the playlists for each tab.
        if (playlistScreenState.playlistsData.playlistSongs.isNotEmpty()) {
            CurrentPlaylist(
                callbacks = callbacks,
                playlist = playlistScreenState.playlistsData.playlistSongs,
//                playlistId = playlistScreenState.playlistsData.playlistState.activePlaylist //Might be wrong
            )
        }
    }
}

@Preview
@Composable
fun MedLrgPreview(){
    MedLrgScreenTabs(
        callbacks = PlaylistCallbacks(),
        playlistScreenState = PlaylistScreenState(),
        modifier = Modifier.background(Color.White)
    )
}