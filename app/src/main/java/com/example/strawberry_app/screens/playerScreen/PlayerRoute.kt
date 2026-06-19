package com.example.strawberry_app.screens.playerScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.music.SongInfo

data class PlayerScreenState(
    val playerValues: PlayerValues = PlayerValues(),
    val currentSong: SongInfo? = null
)

class PlayerCallbacks(
    val formatTime: (Long) -> String,
//    val isConnected: () -> Unit,
    val sendMute: () -> Unit,
    val sendNext: () -> Unit,
    val sendPause: () -> Unit,
//    val sendPlayPause: () -> Unit,
    val sendPlay: () -> Unit,
    val sendPrevious: () -> Unit,
    val sendSeekBackward: () -> Unit,
    val sendSeekForward: () -> Unit,
    val sendSeekTo: (Long) -> Unit,
    val sendStop: () -> Unit,
    val sendStopAfterCurrent: () -> Unit,
    val setVolume: (Int) -> Unit,
    val sendVolumeDown: () -> Unit,
    val sendVolumeUp: () -> Unit,
    val timeChanged: (Long) -> Unit,
    val timerUpdate: () -> Unit
)

@Suppress("ParamsComparedByRef")
@Composable
fun PlayerRoute(
    playerViewModel: PlayerViewModel = hiltViewModel()
){
    val playerValues by playerViewModel.playerState.collectAsStateWithLifecycle()
    val currentSong by playerViewModel.currentSong.collectAsStateWithLifecycle()

    val callbacks = remember {
        PlayerCallbacks(
            formatTime = playerViewModel::formatTime,
//            isConnected = playerViewModel::isConnected,
            sendMute = playerViewModel::sendMute,
            sendNext = playerViewModel::sendNext,
            sendPause = playerViewModel::sendPause,
//            sendPlayPause = playerViewModel::sendPlayPause,
            sendPlay = playerViewModel::sendPlay,
            sendPrevious = playerViewModel::sendPrevious,
            sendSeekBackward = playerViewModel::sendSeekBackward,
            sendSeekForward = playerViewModel::sendSeekForward,
            sendSeekTo = playerViewModel::sendSeekTo,
            sendStop = playerViewModel::sendStop,
            sendStopAfterCurrent = playerViewModel::sendStopAfterCurrent,
            setVolume = playerViewModel::sendVolume,
            sendVolumeDown = playerViewModel::sendVolumeDown,
            sendVolumeUp = playerViewModel::sendVolumeUp,
            timeChanged = playerViewModel::timeChanged,
            timerUpdate = playerViewModel::timerUpdate
        )
    }

    val playerScreenData = PlayerScreenState(playerValues, currentSong)

    PlayerScreen(callbacks, playerScreenData)
}