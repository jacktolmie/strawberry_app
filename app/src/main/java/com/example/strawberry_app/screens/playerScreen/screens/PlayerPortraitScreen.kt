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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
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
import com.example.strawberry_app.screens.playerScreen.composables.VolumeSliderVertComposable

@Composable
fun PlayerMedLrgPortraitScreen(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState,
    screenType: ScreenType,
    modifier: Modifier = Modifier
)
{
    Column(modifier = modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Column(modifier = Modifier
            .fillMaxWidth(.75f)
        )
        {
            // Song text for the song playing
            SongInfoComposable( playerScreenValues)
        }

        if (screenType != ScreenType.SMALL_PHONE)
            Spacer(modifier = Modifier.height(10.dp))

        // Row for cover image and volume controls
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.70f)
            .padding(10.dp),
            horizontalArrangement = Arrangement.Absolute.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            //Song Image
            SongImageComposable(
                imageArt = playerScreenValues.albumArtFile,
                crossfade = true,
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.75f)
                    .aspectRatio(1f)
                    .padding(10.dp)
            )

            if (screenType != ScreenType.SMALL_PHONE)
                Spacer(modifier = Modifier.width(10.dp))

            // Vertical volume slider
            VolumeSliderVertComposable(callbacks, playerScreenValues)
        }

        if (screenType != ScreenType.SMALL_PHONE)
            Spacer(modifier = Modifier.height(10.dp))

        // Time slider
        TimerSlider(callbacks, playerScreenValues)

        // Player control buttons
        MediaBtnComposable(callbacks, playerScreenValues)
    }
}

@Composable
@Preview
fun PlayerMedLrgPortraitScreenPreview(){
    PlayerMedLrgPortraitScreen(
        callbacks = PlayerCallbacks(),
        playerScreenValues = PlayerScreenState(),
        screenType = ScreenType.MEDIUM_PHONE
    )
}