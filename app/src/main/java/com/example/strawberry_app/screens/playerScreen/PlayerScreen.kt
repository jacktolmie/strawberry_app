package com.example.strawberry_app.screens.playerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.ui.theme.icons.play_pause

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlayerScreen(
    callbacks: PlayerCallbacks,
    playerValues: PlayerValues,
    playerRouteData: PlayerRouteData)
{
    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        // Add a active playlist name?
        // Add current song, current time, volume fields.
        Text(text = "Test")
//        VerticalSlider()
    }
}

@Composable
@Preview
fun PlayerScreenPreview(){
    PlayerScreen(
        callbacks = PlayerCallbacks(
            isConnected =  {},
            sendMute =  {},
            sendNext = {},
            sendPause =  {},
            sendPlayPause =  {},
            sendPlay =  {},
            sendPrevious =  {},
            sendSeekBackward =  {},
            sendSeekForward =  {},
            sendSeekTo =  {},
            sendStop =  {},
            sendStopAfterCurrent =  {},
            setVolume =  {},
            sendVolumeDown =  {},
            sendVolumeUp =  {}
        ),
        playerValues = PlayerValues(),
        playerRouteData = PlayerRouteData(play_pause)
    )
}