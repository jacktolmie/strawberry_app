package com.example.strawberry_app.screens.playlistScreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistRepository
import com.example.strawberry_app.screens.playlistScreen.PlaylistState

@Composable
fun PlaylistMediumScreen(
    callbacks: PlaylistCallbacks,
    playlistState: PlaylistState
){

//    Button(
//        onClick =
//        )
//    ){
//        Text(text = "Remove songs")
//    }

}

@Composable
@Preview
fun PlaylistPreview(){
    PlaylistMediumScreen(
        PlaylistCallbacks(),
        PlaylistState()
    )
}