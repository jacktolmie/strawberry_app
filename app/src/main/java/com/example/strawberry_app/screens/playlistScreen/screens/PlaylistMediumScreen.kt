package com.example.strawberry_app.screens.playlistScreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.screens.playlistScreen.composables.MedLrgScreenTabs

@Composable
fun PlaylistMediumScreen(
    callbacks: PlaylistCallbacks,
    playlistScreenState: PlaylistScreenState
){
    MedLrgScreenTabs(
        callbacks = callbacks,
        playlistScreenState = playlistScreenState
    )
}

@Composable
@Preview
fun PlaylistPreview(){
    PlaylistMediumScreen(
        PlaylistCallbacks(),
        PlaylistScreenState()
    )
}