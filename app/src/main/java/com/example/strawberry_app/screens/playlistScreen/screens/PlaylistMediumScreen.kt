package com.example.strawberry_app.screens.playlistScreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks

@Composable
fun PlaylistMediumScreen(
    callbacks: PlaylistCallbacks
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
        PlaylistCallbacks()
    )
}