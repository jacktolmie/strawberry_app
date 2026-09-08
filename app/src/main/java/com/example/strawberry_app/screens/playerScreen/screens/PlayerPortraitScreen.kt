package com.example.strawberry_app.screens.playerScreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.composables.SongImageComposable
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.functions.bottomPadding
import com.example.strawberry_app.screens.functions.spacerSize
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState
import com.example.strawberry_app.screens.playerScreen.composables.MediaBtnComposable
import com.example.strawberry_app.screens.playerScreen.composables.SongInfoComposable
import com.example.strawberry_app.screens.playerScreen.composables.TimerSlider
import com.example.strawberry_app.screens.playerScreen.composables.VolumeSliderVert

@Composable
fun PlayerPortraitScreen(
    callbacks: PlayerCallbacks,
    playerScreenState: PlayerScreenState,
    deviceType: DeviceTypesBreakdown,
    modifier: Modifier = Modifier
) {
    val space = spacerSize(deviceType)

    Column(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(bottom = bottomPadding(deviceType))
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        // Song text for the song playing
        SongInfoComposable( playerScreenState)

        Spacer(modifier = Modifier.height(space))

        // Row for cover image and volume controls
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.70f)
            .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Spacer(modifier = Modifier.height(space))

            //Song Image
            SongImageComposable(
                imageArt = playerScreenState.albumArtFile,
                crossfade = true,
                Modifier
                    .fillMaxWidth(.7f)
                    .aspectRatio(1f)
                    .padding(space)
            )

            Spacer(modifier = Modifier.height(space))

            // Vertical volume slider
            VolumeSliderVert(
                callbacks = callbacks,
                playerScreenValues = playerScreenState,
                modifier = Modifier.padding(end = space)
            )
        }

        Spacer(modifier = Modifier.height(space))

        // Time slider
        TimerSlider(callbacks, playerScreenState)

        // Player control buttons
        MediaBtnComposable(callbacks, playerScreenState)
    }
}

@Composable
@Preview
fun PlayerPortraitScreenPreview(){
    PlayerPortraitScreen(
        callbacks = PlayerCallbacks(),
        playerScreenState = PlayerScreenState(),
        deviceType = DeviceTypesBreakdown.PHONE_PORTRAIT
    )
}