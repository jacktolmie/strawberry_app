package com.example.strawberry_app.screens.playerScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.example.strawberry_app.screens.ServerGuiValues
import com.example.strawberry_app.screens.playerScreen.screens.PlayerMedLrgPortraitScreen
import java.io.File

data class PlayerScreenState(
    val playerValues: ServerGuiValues = ServerGuiValues(),
    val albumArtFile: File? = null
)

class PlayerCallbacks(
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

@Composable
fun PlayerRoute(
    isCompact: Boolean,
    playerViewModel: PlayerViewModel = hiltViewModel()
){
    val playerValues by playerViewModel.serverUpdates.collectAsStateWithLifecycle()
    val albumArtFile by playerViewModel.albumArtFile.collectAsStateWithLifecycle()

    val callbacks = remember {
        PlayerCallbacks(
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

    val playerScreenData = PlayerScreenState(playerValues, albumArtFile)

    if(isCompact){
//        PlayerSmallScreenPortrait()
        println("nada")
    } else {
        PlayerMedLrgPortraitScreen(callbacks, playerScreenData)
    }

}