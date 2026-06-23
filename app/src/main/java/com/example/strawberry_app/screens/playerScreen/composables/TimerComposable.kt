package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

//Timer slider for player screen.
@Composable
fun TimerSlider(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState
) {
    var sliderPosition by remember { mutableFloatStateOf(playerScreenValues.playerValues.currentTime.toFloat()) }

    Text(
        text = if (playerScreenValues.playerValues.currentTime > 0) callbacks.formatTime(
            playerScreenValues.playerValues.currentTime
        ) else "0"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(text = "0")
        Slider(
            value = playerScreenValues.playerValues.currentTime.toFloat(),
            onValueChange = {
                sliderPosition = it
                callbacks.timeChanged(it.toLong())
            },
            onValueChangeFinished = {
                callbacks.sendSeekTo(sliderPosition.toLong())
            },
            valueRange = if (playerScreenValues.playerValues.currentSong.length > 0) 0f..playerScreenValues.playerValues.currentSong.length.toFloat() else 0f..1f,
            modifier = Modifier
                .weight(1F)
                .padding(start = 10.dp, end = 10.dp)
        )
        Text(
            text = if (playerScreenValues.playerValues.currentSong.length > 0) callbacks.formatTime(
                playerScreenValues.playerValues.currentSong.length
            ) else "0"
        )

    }
}