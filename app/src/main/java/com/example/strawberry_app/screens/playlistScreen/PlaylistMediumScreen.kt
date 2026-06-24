package com.example.strawberry_app.screens.playlistScreen

import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.music.PlaylistRepository
import androidx.compose.runtime.collectAsState

@Composable
fun PlaylistMediumScreen(
    callbacks: PlaylistCallbacks
){



}

@Composable
@Preview
fun PlaylistPreview(){
    PlaylistMediumScreen(
        PlaylistCallbacks()
    )
}