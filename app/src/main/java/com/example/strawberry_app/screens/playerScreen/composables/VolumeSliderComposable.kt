package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.VerticalSlider
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState
import com.example.strawberry_app.ui.theme.icons.volume_down
import com.example.strawberry_app.ui.theme.icons.volume_off
import com.example.strawberry_app.ui.theme.icons.volume_up

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun VolumeSliderVertComposable(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState
){
    val volumeSliderState = rememberSliderState(valueRange = 0f..100f)
    LaunchedEffect(playerScreenValues.playerValues.volume) { volumeSliderState.value = playerScreenValues.playerValues.volume.toFloat() }

    volumeSliderState.onValueChangeFinished = { callbacks.setVolume(volumeSliderState.value.toInt())}

    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly  //spacedBy(5.dp)
    ) {
        CreateButton(volume_up, "Volume up", callbacks.sendVolumeUp)

        VerticalSlider(
            state = volumeSliderState,
            modifier = Modifier
                .fillMaxHeight(.75f)
                .progressSemantics(volumeSliderState.value, 0f..100f),
            reverseDirection = true
        )

        CreateButton(volume_down, "Volume down", callbacks.sendVolumeDown)
        CreateButton(volume_off, "Volume off", callbacks.sendMute)
    }
}