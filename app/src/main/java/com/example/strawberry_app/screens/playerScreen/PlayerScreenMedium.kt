package com.example.strawberry_app.screens.playerScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalSlider
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.ui.theme.icons.fast_forward
import com.example.strawberry_app.ui.theme.icons.fast_rewind
import com.example.strawberry_app.ui.theme.icons.pause
import com.example.strawberry_app.ui.theme.icons.play_arrow
import com.example.strawberry_app.ui.theme.icons.play_pause
import com.example.strawberry_app.ui.theme.icons.skip_next
import com.example.strawberry_app.ui.theme.icons.skip_previous
import com.example.strawberry_app.ui.theme.icons.stop
import com.example.strawberry_app.ui.theme.icons.volume_down
import com.example.strawberry_app.ui.theme.icons.volume_off
import com.example.strawberry_app.ui.theme.icons.volume_up
import kotlinx.coroutines.delay
import kotlinx.coroutines.time.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlayerScreen(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState)
{
    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .navigationBarsPadding()
        .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Column(modifier = Modifier
            .fillMaxWidth(.75f)
        )
        {
            // Song text for the song playing
            Text(
                text = playerScreenValues.currentSong?.title ?: "",
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 10000,
                    spacing = MarqueeSpacing.fractionOfContainer(0.1f)
                ),
                maxLines = 1
            )

            val artistAlbum = listOfNotNull(
                playerScreenValues.currentSong?.artist?.takeIf { it.isNotBlank() },
                playerScreenValues.currentSong?.album?.takeIf { it.isNotBlank() }
            ).joinToString(" • ")

            // Album/Artist text.
            Text(
                text = artistAlbum,
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 10000,
                    spacing = MarqueeSpacing.fractionOfContainer(0.1f)
                ),
                maxLines = 1
            )
        }

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
            Image(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(.75f)
                .aspectRatio(1f)
                .padding(10.dp),
                // Find the current image url or default strawberry image
                painter = painterResource(
                    R.drawable.strawberry),
                contentDescription = "Test Image",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp).border(2.dp, Color.Blue))

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

        Spacer(modifier = Modifier.height(10.dp))

        // Time slider
        var sliderPosition by remember { mutableFloatStateOf(playerScreenValues.playerValues.currentTime.toFloat()) }

        LaunchedEffect(Unit) {
            println("playerscreen launch effect called")
            while(true) {
                delay(1000.milliseconds)
                println("playerviewmodel: ${playerScreenValues.playerValues.playState}")
                if (playerScreenValues.playerValues.playState == PlayState.PLAYING) {
                    callbacks.timerUpdate()
                }
            }
        }

        Text(text = if (playerScreenValues.playerValues.currentTime > 0) callbacks.formatTime(playerScreenValues.playerValues.currentTime) else "0")

        Row(modifier = Modifier
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
                    callbacks.sendSeekTo(sliderPosition.toLong())},
                valueRange = if (playerScreenValues.playerValues.songLength > 0) 0f..playerScreenValues.playerValues.songLength.toFloat() else 0f..1f,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 10.dp, end = 10.dp)
            )
            Text(text = if (playerScreenValues.playerValues.songLength > 0) callbacks.formatTime(playerScreenValues.playerValues.songLength) else "0")
        }


        // Player control buttons
        Row(modifier = Modifier
            .widthIn(max = 400.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            CreateButton(skip_previous, "Previous", callbacks.sendPrevious)
            CreateRepeatButton(fast_rewind, "Fast Rewind", callbacks.sendSeekBackward)

            // Show proper play or pause button, and the proper callback
            CreateButton(
                image = when(playerScreenValues.playerValues.playState){
                    PlayState.PAUSED -> play_arrow
                    PlayState.PLAYING -> pause
                    else -> play_pause
                },
                "Play / Pause",
                if(playerScreenValues.playerValues.playState == PlayState.PLAYING) callbacks.sendPause else callbacks.sendPlay
            )
            // Send both stop and stop after current for the stop button.
            CreateLongPressButton(stop, "Stop", callbacks.sendStop, callbacks.sendStopAfterCurrent)
            CreateRepeatButton(fast_forward, "Fast Forward", callbacks.sendSeekForward)
            CreateButton(skip_next, "Next", callbacks.sendNext)
            }
    }
}

@Composable
@Preview
fun PlayerScreenPreview(){
    PlayerScreen(
        callbacks = PlayerCallbacks(
            formatTime = {""},
//            isConnected =  {},
            sendMute =  {},
            sendNext = {},
            sendPause =  {},
//            sendPlayPause =  {},
            sendPlay =  {},
            sendPrevious =  {},
            sendSeekBackward =  {},
            sendSeekForward =  {},
            sendSeekTo =  {},
            sendStop =  {},
            sendStopAfterCurrent =  {},
            setVolume =  {},
            sendVolumeDown =  {},
            sendVolumeUp =  {},
            timeChanged = {},
            timerUpdate = {}
        ),
        playerScreenValues = PlayerScreenState()
    )
}