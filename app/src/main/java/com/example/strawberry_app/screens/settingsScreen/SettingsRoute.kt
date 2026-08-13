package com.example.strawberry_app.screens.settingsScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.ConnectionViewModel
import com.example.strawberry_app.network.SettingsGuiData
import com.example.strawberry_app.screens.navigation.ScreenType
import com.example.strawberry_app.screens.settingsScreen.screens.SettingsMedLrgPortraitScreen
import com.example.strawberry_app.server.ServerViewModel
import com.example.strawberry_app.server.SettingsUiState

data class SettingsScreenState(
    val connectionState: ConnectionState,
    val hasNetwork: Boolean,
    val serverUiState: SettingsUiState,
    val settingsGuiData: SettingsGuiData
)

class SettingsCallbacks(
    val onIpChanged: (String) -> Unit = {},
    val onPortChanged: (String) -> Unit = {},
    val onPasswordChanged: (String) -> Unit = {},
    val onSaveClicked: () -> Unit = {},
    val onCancelClicked: () -> Unit = {},
    val onDisconnectClicked: () -> Unit = {},
    val onConnectClicked: () -> Unit = {}
)

@Composable
fun SettingsRoute(
    isPortrait: Boolean,
    screenType: ScreenType,
    serverViewModel: ServerViewModel = hiltViewModel(),
    connectionViewModel: ConnectionViewModel = hiltViewModel()
) {
    val uiState by serverViewModel.uiState.collectAsStateWithLifecycle()
    val connectionState by connectionViewModel
        .connectionState
        .collectAsStateWithLifecycle()
    val hasNetwork by connectionViewModel.hasNetwork.collectAsStateWithLifecycle()

    val callbacks = remember {
        SettingsCallbacks(
            onIpChanged = serverViewModel::onIpChanged,
            onPortChanged = serverViewModel::onPortChanged,
            onPasswordChanged = serverViewModel::onPasswordChanged,
            onSaveClicked = serverViewModel::save,
            onCancelClicked = serverViewModel::cancel,
            onDisconnectClicked = connectionViewModel::manualDisconnect,
            onConnectClicked = connectionViewModel::manualConnect
        )
    }

    val routeData by connectionViewModel.routeData.collectAsStateWithLifecycle()

    val settingsScreenState = SettingsScreenState(
        serverUiState = uiState,
        connectionState = connectionState,
        hasNetwork = hasNetwork,
        settingsGuiData = routeData
    )

    SettingsMedLrgPortraitScreen(
        callbacks = callbacks,
        isPortrait = isPortrait,
        state = settingsScreenState,
        screenType = screenType
    )

}