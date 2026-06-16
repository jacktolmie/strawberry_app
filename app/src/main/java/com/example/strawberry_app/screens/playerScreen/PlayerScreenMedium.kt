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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import java.lang.Math.pow
import kotlin.math.pow

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PlayerScreen(
    callbacks: PlayerCallbacks,
    playerValues: PlayerValues)
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
                text = "This is the song area. It is a very long song and needs to scroll",
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 10000,
                    spacing = MarqueeSpacing.fractionOfContainer(0.1f)
                ),
                maxLines = 1
            )

            // Album/Artist text.
            Text(
                text = "This will be the album/group, It is a very long song and needs to scroll",
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
                LaunchedEffect(playerValues.volume) { volumeSliderState.value = playerValues.volume.toFloat() }

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
        println("NetworkManager songLength: ${playerValues.songLength}")

        // Time slider
        var sliderPosition by remember { mutableFloatStateOf(playerValues.currentTime.toFloat()) }

//        Text(text = formatTime(playerValues.currentTime))
        Text(text = if (playerValues.currentTime > 0) formatTime(playerValues.currentTime) else "0")

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(text = "0")
            Slider(
                value = playerValues.currentTime.toFloat(),
                onValueChange = {
                    sliderPosition = it
                    playerValues.copy(currentTime = it.toLong())
                },
                onValueChangeFinished = {
                    println("PlayerScreen slider position: ${sliderPosition.toInt()}")
                    callbacks.sendSeekTo(sliderPosition.toInt())},
                valueRange = if (playerValues.songLength > 0) 0f..playerValues.songLength.toFloat() else 0f..1f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            )
            Text(text = if (playerValues.songLength > 0) formatTime(playerValues.songLength) else "0")
        }


        // Player control buttons
        Row(modifier = Modifier
            .widthIn(max = 400.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            CreateButton(skip_previous, "Previous", callbacks.sendPrevious)
            CreateButton(fast_rewind, "Fast Rewind", callbacks.sendSeekBackward)

            // Show proper play or pause button, and the proper callback
            CreateButton(
                image = when(playerValues.playState){
                    PlayState.PAUSED -> play_arrow
                    PlayState.PLAYING -> pause
                    else -> play_pause
                },
                "Play / Pause",
                if(playerValues.playState == PlayState.PLAYING) callbacks.sendPause else callbacks.sendPlay
            )
            CreateButton(stop, "Stop", callbacks.sendStop)
            CreateButton(fast_forward, "Fast Forward", callbacks.sendSeekForward)
            CreateButton(skip_next, "Next", callbacks.sendNext)
            }
    }
}

@Composable
fun CreateButton(image: ImageVector, description: String, control: () -> Unit) {
    IconButton(
        modifier = Modifier.size(48.dp),
        onClick = { control() }
    ) {
        Icon(
            imageVector = image,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
    }
}

fun formatTime(time: Long): String {
    val hours = time / 3_600_000
    val minutes = (time % 3_600_000) / 60_000
    val seconds = (time % 60_000) / 1_000
    return "%d:%02d:%02d".format(hours, minutes, seconds)
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
        playerValues = PlayerValues()
    )
}