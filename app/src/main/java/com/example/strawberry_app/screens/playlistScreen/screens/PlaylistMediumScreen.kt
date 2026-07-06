package com.example.strawberry_app.screens.playlistScreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistRepository
import com.example.strawberry_app.screens.playlistScreen.PlaylistState
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData
import com.example.strawberry_app.screens.playlistScreen.composables.MedLrgScreenTabs

@Composable
fun PlaylistMediumScreen(
    callbacks: PlaylistCallbacks,
    playlistsData: PlaylistsData
){
    MedLrgScreenTabs(
        callbacks = callbacks,
        playlistsData = playlistsData
    )
}

@Composable
@Preview
fun PlaylistPreview(){
    PlaylistMediumScreen(
        PlaylistCallbacks(),
        PlaylistsData()
    )
}