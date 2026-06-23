package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.PlayState
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState
import com.example.strawberry_app.ui.theme.icons.fast_forward
import com.example.strawberry_app.ui.theme.icons.fast_rewind
import com.example.strawberry_app.ui.theme.icons.pause
import com.example.strawberry_app.ui.theme.icons.play_arrow
import com.example.strawberry_app.ui.theme.icons.play_pause
import com.example.strawberry_app.ui.theme.icons.skip_next
import com.example.strawberry_app.ui.theme.icons.skip_previous
import com.example.strawberry_app.ui.theme.icons.stop

// Media buttons for player screen.
@Composable
fun MediaBtnComposable(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState
) {
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
//                if(playerScreenValues.playerValues.playState == PlayState.PLAYING) callbacks.sendPause else callbacks.sendPlay
                callbacks.sendPlayPause
            )
            // Send both stop and stop after current for the stop button.
            CreateLongPressButton(stop, "Stop", callbacks.sendStop, callbacks.sendStopAfterCurrent)
            CreateRepeatButton(fast_forward, "Fast Forward", callbacks.sendSeekForward)
            CreateButton(skip_next, "Next", callbacks.sendNext)
            }
}