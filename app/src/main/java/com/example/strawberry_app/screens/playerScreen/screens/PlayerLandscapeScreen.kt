package com.example.strawberry_app.screens.playerScreen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.composables.SongImageComposable
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.functions.spacerSize
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState
import com.example.strawberry_app.screens.playerScreen.composables.MediaBtnComposable
import com.example.strawberry_app.screens.playerScreen.composables.SongInfoComposable
import com.example.strawberry_app.screens.playerScreen.composables.TimerSlider
import com.example.strawberry_app.screens.playerScreen.composables.VolumeSliderHoriz

@Composable
fun PlayerLandscapeScreen(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState,
    deviceType: DeviceTypesBreakdown,
    modifier: Modifier = Modifier
){
    val space = spacerSize(deviceType)

    Row(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        //Song Image
        SongImageComposable(
            imageArt = playerScreenValues.albumArtFile,
            crossfade = true,
            Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(space)
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Song text for the song playing
                SongInfoComposable(playerScreenValues)
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Time slider
                TimerSlider(callbacks, playerScreenValues)
            }

            // Player control buttons
            MediaBtnComposable(callbacks, playerScreenValues)

            // Volume Slider
            VolumeSliderHoriz(callbacks, playerScreenValues)
        }
    }
}

@Preview(name = "Phone Landscape", showBackground = true, widthDp = 800, heightDp = 360)
@Composable
fun PlayerLandscapePreview(){
    PlayerLandscapeScreen(
        callbacks = PlayerCallbacks(),
        playerScreenValues = PlayerScreenState(),
        deviceType = DeviceTypesBreakdown.PHONE_PORTRAIT
    )
}

