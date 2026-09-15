package com.example.strawberry_app.screens.playerScreen.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun PlayerLayout(
    callbacks: PlayerCallbacks,
    onNavigateToSettings: () -> Unit,
    playerScreenData: PlayerScreenState,
    isPortrait: Boolean,
    deviceType: DeviceTypesBreakdown,
    modifier: Modifier = Modifier
    ){

    if (isPortrait) {
            PlayerPortraitScreen(
                callbacks = callbacks,
                playerScreenState = playerScreenData,
                deviceType = deviceType,
                onNavigateToSettings = onNavigateToSettings
            )
    } else {
        PlayerLandscapeScreen(
            callbacks = callbacks,
            playerScreenValues = playerScreenData,
            deviceType = deviceType
        )
    }
}