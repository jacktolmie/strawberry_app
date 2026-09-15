package com.example.strawberry_app.screens.settingsScreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.strawberry_app.R
import com.example.strawberry_app.network.ConnectionState.Connected
import com.example.strawberry_app.network.SettingsGuiData
import com.example.strawberry_app.screens.composables.TopBar
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.devices.isDeviceTablet
import com.example.strawberry_app.screens.devices.isSmallDevice
import com.example.strawberry_app.screens.functions.spacerSize
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.screens.settingsScreen.SettingsScreenState
import com.example.strawberry_app.screens.settingsScreen.composables.ConnectionState
import com.example.strawberry_app.screens.settingsScreen.composables.MedLrgScreenBtns
import com.example.strawberry_app.screens.settingsScreen.composables.SmallScreenBtns
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxIp
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPassword
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPortHoriz
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun SettingsScreen(
    callbacks: SettingsCallbacks,
    deviceType: DeviceTypesBreakdown,
    isPortrait: Boolean,
    onNavigateBack: () -> Unit,
    state: SettingsScreenState,
    modifier: Modifier = Modifier
) {
    val spacing = spacerSize(deviceType)
    val useSmallButtons = isSmallDevice(deviceType)

    Scaffold(
        topBar = {
            TopBar(
                heading = R.string.settings_title,
                onClick = {},
                onNavigationBack = { onNavigateBack() },
                showMoreOptions = false,
                showBackButton = !isDeviceTablet(deviceType)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(10.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // your existing content, minus the TextBox title
            Spacer(modifier = Modifier.height(spacing))

            TextboxIp(state.serverUiState, callbacks)

            Spacer(modifier = Modifier.height(spacing))

            TextboxPortHoriz(state.serverUiState, callbacks)

            Spacer(modifier = Modifier.height(spacing))

            TextboxPassword(state.serverUiState, callbacks)

            Spacer(modifier = Modifier.height(spacing))

            if (isPortrait && !useSmallButtons){
                MedLrgScreenBtns(
                    serverUiState = state.serverUiState,
                    callbacks = callbacks,
                    connectionState = state.connectionState,
                    hasNetwork = state.hasNetwork
                )
            } else {
                SmallScreenBtns(
                    serverUiState = state.serverUiState,
                    callbacks = callbacks,
                    connectionState = state.connectionState,
                    hasNetwork = state.hasNetwork
                )
            }

            ConnectionState(state.settingsGuiData)

        }
    }
}

@Preview
@Composable
fun SettingsPreview(){
    SettingsScreen(
        state = SettingsScreenState(
            serverUiState = SettingsUiState(
                ip = "192.168.1.201",
                port = "5000",
                password = "",
                hasChanged = false,
                isPortValid = true
            ),
            connectionState = Connected,
            hasNetwork = true,
            settingsGuiData = SettingsGuiData()
        ),
        callbacks = SettingsCallbacks(
            onIpChanged = {},
            onPortChanged = {},
            onPasswordChanged = {},
            onSaveClicked = {},
            onCancelClicked = {},
            onDisconnectClicked = {},
            onConnectClicked = {}
        ),
        isPortrait = true,
        deviceType = DeviceTypesBreakdown.SMALL_PHONE_PORTRAIT,
        onNavigateBack = {}
    )
}