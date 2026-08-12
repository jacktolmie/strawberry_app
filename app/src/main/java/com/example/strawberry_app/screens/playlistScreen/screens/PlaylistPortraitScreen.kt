package com.example.strawberry_app.screens.playlistScreen.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.navigation.ScreenType
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.screens.playlistScreen.composables.CurrentPlaylist
import com.example.strawberry_app.screens.playlistScreen.composables.TabListing

@Composable
fun PlaylistPortraitScreen(
    callbacks: PlaylistCallbacks,
    isPortrait: Boolean,
    playlistScreenState: PlaylistScreenState,
    screenType: ScreenType,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)
    ){
        // Create the scrollable tab
        if (playlistScreenState.playlistsData.playlists.isNotEmpty()) {
            TabListing(
                callbacks = callbacks,
                isPortrait = isPortrait,
                playlistScreenState = playlistScreenState,
                screenType = screenType
            )
        }
        // Create the playlists for each tab.
        if (playlistScreenState.playlistsData.playlistSongs.isNotEmpty()) {
            CurrentPlaylist(
                albumArtCollection = playlistScreenState.albumArtFile,
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
    PlaylistPortraitScreen(
        callbacks = PlaylistCallbacks(),
        isPortrait = true,
        playlistScreenState = PlaylistScreenState(),
        screenType = ScreenType.MEDIUM_PHONE
    )
}