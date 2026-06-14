package com.example.strawberry_app.screens.playerScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


class PlayerCallbacks(
    val isConnected: () -> Unit,
    val sendMute: () -> Unit,
    val sendNext: () -> Unit,
    val sendPause: () -> Unit,
    val sendPlayPause: () -> Unit,
    val sendPlay: () -> Unit,
    val sendPrevious: () -> Unit,
    val sendSeekBackward: () -> Unit,
    val sendSeekForward: () -> Unit,
    val sendSeekTo: (Int) -> Unit,
    val sendStop: () -> Unit,
    val sendStopAfterCurrent: () -> Unit,
    val setVolume: (Int) -> Unit,
    val sendVolumeDown: () -> Unit,
    val sendVolumeUp: () -> Unit
)

@Suppress("ParamsComparedByRef")
@Composable
fun PlayerRoute(
    playerViewModel: PlayerViewModel = hiltViewModel()
){
    val playerValues by playerViewModel.playerState.collectAsStateWithLifecycle()

    val callbacks = remember {
        PlayerCallbacks(
            isConnected = playerViewModel::isConnected,
            sendMute = playerViewModel::sendMute,
            sendNext = playerViewModel::sendNext,
            sendPause = playerViewModel::sendPause,
            sendPlayPause = playerViewModel::sendPlayPause,
            sendPlay = playerViewModel::sendPlay,
            sendPrevious = playerViewModel::sendPrevious,
            sendSeekBackward = playerViewModel::sendSeekBackward,
            sendSeekForward = playerViewModel::sendSeekForward,
            sendSeekTo = playerViewModel::sendSeekTo,
            sendStop = playerViewModel::sendStop,
            sendStopAfterCurrent = playerViewModel::sendStopAfterCurrent,
            setVolume = playerViewModel::sendVolume,
            sendVolumeDown = playerViewModel::sendVolumeDown,
            sendVolumeUp = playerViewModel::sendVolumeUp
        )
    }

    PlayerScreen(callbacks, playerValues)
}