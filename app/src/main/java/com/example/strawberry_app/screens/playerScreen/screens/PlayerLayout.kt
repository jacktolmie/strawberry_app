package com.example.strawberry_app.screens.playerScreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.classes.DeviceState
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun PlayerLayout(
    callbacks: PlayerCallbacks,
    deviceState: DeviceState,
    onNavigateToSettings: () -> Unit,
    playerScreenData: PlayerScreenState,
    modifier: Modifier = Modifier
    ){

    if (deviceState.isPortrait) {
            PlayerPortraitScreen(
                callbacks = callbacks,
                playerScreenState = playerScreenData,
                deviceType = deviceState.deviceType,
                onNavigateToSettings = onNavigateToSettings,
                modifier = modifier
            )
    } else {
        PlayerLandscapeScreen(
            callbacks = callbacks,
            playerScreenValues = playerScreenData,
            deviceType = deviceState.deviceType,
            modifier = modifier
        )
    }
}