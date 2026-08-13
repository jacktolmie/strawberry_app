package com.example.strawberry_app.screens.playerScreen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.SongImageComposable
import com.example.strawberry_app.screens.navigation.ScreenType
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
    screenType: ScreenType,
    modifier: Modifier = Modifier
){
    val space = if (screenType == ScreenType.SMALL_PHONE) 0.dp else 10.dp

    Row(
        modifier = modifier
            .fillMaxSize(),
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
        screenType = ScreenType.MEDIUM_PHONE
    )
}

