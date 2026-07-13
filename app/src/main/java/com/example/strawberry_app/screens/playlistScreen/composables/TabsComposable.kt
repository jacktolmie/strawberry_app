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
import com.example.strawberry_app.screens.playlistScreen.PlaylistState
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData

@Composable
fun MedLrgScreenTabs(
    callbacks: PlaylistCallbacks,
    playlistsData: PlaylistsData,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)
    ){
        // Create the scrollable tab
        if (playlistsData.playlists.isNotEmpty()) {
            TabListing(callbacks, playlistsData)
        }

        // Create the playlists for each tab.
        if (playlistsData.playlistSongs.isNotEmpty()) {
            CurrentPlaylist(
                playlist = playlistsData.playlistSongs,
//                playerScreenState = playlistsData.playlistState
            )
        }
    }
}

@Preview
@Composable
fun MedLrgPreview(){
    MedLrgScreenTabs(
        PlaylistCallbacks(),
        PlaylistsData(
            playlistState = PlaylistState(),
            samplePlaylists(),
            sampleSongList()
        ),
        Modifier.background(Color.White),
    )
}