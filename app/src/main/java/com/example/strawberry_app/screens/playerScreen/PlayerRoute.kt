package com.example.strawberry_app.screens.playerScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.strawberry_app.ui.theme.icons.pause
import com.example.strawberry_app.ui.theme.icons.play_arrow
import com.example.strawberry_app.ui.theme.icons.play_pause

class PlayerRouteData(
    val playStateIcon: ImageVector
)

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
    playerValues: PlayerValues,
    viewModel: PlayerViewModel = hiltViewModel()

){
    val callbacks = remember {
        PlayerCallbacks(
            isConnected = viewModel::isConnected,
            sendMute = viewModel::sendMute,
            sendNext = viewModel::sendNext,
            sendPause = viewModel::sendPause,
            sendPlayPause = viewModel::sendPlayPause,
            sendPlay = viewModel::sendPlay,
            sendPrevious = viewModel::sendPrevious,
            sendSeekBackward = viewModel::sendSeekBackward,
            sendSeekForward = viewModel::sendSeekForward,
            sendSeekTo = viewModel::sendSeekTo,
            sendStop = viewModel::sendStop,
            sendStopAfterCurrent = viewModel::sendStopAfterCurrent,
            setVolume = viewModel::setVolume,
            sendVolumeDown = viewModel::sendVolumeDown,
            sendVolumeUp = viewModel::sendVolumeUp
        )
    }

    val playPause by viewModel.playerState.collectAsState()
    val playPauseState = when(playPause.playState){
        PlayState.PLAYING -> pause
        PlayState.PAUSED -> play_arrow
        else -> play_pause
    }

    val routeData = PlayerRouteData(
        playStateIcon = playPauseState
    )


    PlayerScreen(callbacks, playerValues, routeData)
}