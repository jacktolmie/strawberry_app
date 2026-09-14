package com.example.strawberry_app.screens.devices.phone

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.composables.TopBar
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState
import com.example.strawberry_app.screens.playerScreen.screens.PlayerLandscapeScreen
import com.example.strawberry_app.screens.playerScreen.screens.PlayerPortraitScreen

@Composable
fun PhoneLayout(
    callbacks: PlayerCallbacks,
    onNavigateToSettings: () -> Unit,
    playerScreenData: PlayerScreenState,
    isPortrait: Boolean,
    deviceType: DeviceTypesBreakdown,
    modifier: Modifier = Modifier
    ){

    Scaffold(
        topBar = { TopBar(onClick = onNavigateToSettings) }
    ) { paddingValues ->

        if (isPortrait) {
            PlayerPortraitScreen(
                callbacks = callbacks,
                playerScreenState = playerScreenData,
                deviceType = deviceType,
                modifier = modifier.padding(paddingValues)
            )
        } else {
            PlayerLandscapeScreen(
                callbacks = callbacks,
                playerScreenValues = playerScreenData,
                deviceType = deviceType,
                modifier = modifier.padding(paddingValues)
            )
        }
    }
}