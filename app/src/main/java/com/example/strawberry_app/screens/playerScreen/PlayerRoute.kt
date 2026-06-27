package com.example.strawberry_app.screens.playerScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.screens.ServerValues

data class PlayerScreenState(
    val playerValues: ServerValues = ServerValues()
)

class PlayerCallbacks(
    val formatTime: (Long) -> String = {""},
    val sendMute: () -> Unit = {},
    val sendNext: () -> Unit = {},
    val sendRestartPrevious: () -> Unit = {},
    val sendPlayPause: () -> Unit = {},
    val sendPrevious: () -> Unit = {},
    val sendSeekBackward: () -> Unit = {},
    val sendSeekForward: () -> Unit = {},
    val sendSeekTo: (Long) -> Unit = {},
    val sendStop: () -> Unit = {},
    val sendStopAfterCurrent: () -> Unit = {},
    val setVolume: (Int) -> Unit = {},
    val sendVolumeDown: () -> Unit = {},
    val sendVolumeUp: () -> Unit = {},
    val timeChanged: (Long) -> Unit = {},
)

@Suppress("ParamsComparedByRef")
@Composable
fun PlayerRoute(
    playerViewModel: PlayerViewModel = hiltViewModel()
){
    val playerValues by playerViewModel.serverUpdates.collectAsStateWithLifecycle()

    val callbacks = remember {
        PlayerCallbacks(
            formatTime = playerViewModel::formatTime,
            sendMute = playerViewModel::sendMute,
            sendNext = playerViewModel::sendNext,
            sendRestartPrevious = playerViewModel::sendRestartPrevious,
            sendPlayPause = playerViewModel::sendPlayPause,
            sendPrevious = playerViewModel::sendPrevious,
            sendSeekBackward = playerViewModel::sendSeekBackward,
            sendSeekForward = playerViewModel::sendSeekForward,
            sendSeekTo = playerViewModel::sendSeekTo,
            sendStop = playerViewModel::sendStop,
            sendStopAfterCurrent = playerViewModel::sendStopAfterCurrent,
            setVolume = playerViewModel::sendVolume,
            sendVolumeDown = playerViewModel::sendVolumeDown,
            sendVolumeUp = playerViewModel::sendVolumeUp,
            timeChanged = playerViewModel::timeChanged
        )
    }

    val playerScreenData = PlayerScreenState(playerValues) //, currentSong)

    PlayerScreen(callbacks, playerScreenData)
}