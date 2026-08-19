package com.example.strawberry_app.screens.playerScreen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.composables.SongImageComposable
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.functions.bottomPadding
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState
import com.example.strawberry_app.screens.playerScreen.composables.MediaBtnComposable
import com.example.strawberry_app.screens.playerScreen.composables.SongInfoComposable
import com.example.strawberry_app.screens.playerScreen.composables.TimerSlider
import com.example.strawberry_app.screens.playerScreen.composables.VolumeSliderHoriz

@Composable
fun PlayerTabletPortraitScreen(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxHeight()
            .padding(bottom = bottomPadding(DeviceTypesBreakdown.TABLET_PORTRAIT)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        // Song Information
        SongInfoComposable(playerScreenValues = playerScreenValues)

        //Song Image
        SongImageComposable(
            imageArt = playerScreenValues.albumArtFile,
            crossfade = true,
            Modifier
                .fillMaxWidth(.4f)
                .aspectRatio(1f)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Time slider
            TimerSlider(callbacks, playerScreenValues)
            // Player control buttons
            MediaBtnComposable(callbacks, playerScreenValues)
        }

        // Volume Slider
        VolumeSliderHoriz(callbacks, playerScreenValues)
    }

}

@Preview(name = "Tablet Portrait", showBackground = true, widthDp = 800, heightDp = 640)
@Composable
fun TabletLayoutPreview() {
    PlayerTabletPortraitScreen(
        callbacks = PlayerCallbacks(),
        playerScreenValues = PlayerScreenState()
    )

}