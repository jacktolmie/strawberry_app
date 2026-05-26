package com.example.strawberry_app.screens.settingsScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.network.ConnectionViewModel
import com.example.strawberry_app.server.ServerViewModel

@Suppress("ParamsComparedByRef")
@Composable
fun SettingsRoute(
    serverViewModel: ServerViewModel = hiltViewModel(),
    connectionViewModel: ConnectionViewModel = hiltViewModel()
) {
    val uiState by serverViewModel.uiState.collectAsStateWithLifecycle()
    val connectionState by connectionViewModel
        .connectionState
        .collectAsStateWithLifecycle()

    SettingsScreen(
        serverUiState = uiState,
        connectionState = connectionState,

        onDisconnectClicked = connectionViewModel::manualDisconnect,

        onIpChanged = serverViewModel::onIpChanged,
        onPortChanged = serverViewModel::onPortChanged,
        onPasswordChanged = serverViewModel::onPasswordChanged,
        onSaveClicked = serverViewModel::save,
        onCancelClicked = serverViewModel::cancel
    )
}