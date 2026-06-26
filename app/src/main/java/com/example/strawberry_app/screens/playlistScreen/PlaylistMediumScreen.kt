package com.example.strawberry_app.screens.playlistScreen

import androidx.compose.material3.Button
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.music.PlaylistRepository
import androidx.compose.runtime.collectAsState
import com.example.strawberry_app.network.protocol.OutgoingMessage

@Composable
fun PlaylistMediumScreen(
    callbacks: PlaylistCallbacks
){

    Button(
        onClick =
        )
    ){
        Text(text = "Remove songs")
    }

}

@Composable
@Preview
fun PlaylistPreview(){
    PlaylistMediumScreen(
        PlaylistCallbacks()
    )
}