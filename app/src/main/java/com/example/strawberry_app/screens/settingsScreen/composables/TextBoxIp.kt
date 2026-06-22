package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxIp(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks
){
    TextBox(R.string.settings_ip, MaterialTheme.typography.bodyLarge)

    TextFieldBox(
        serverUiState.ip,
        callbacks.onIpChanged,
        R.string.settings_ip_info,
        error = serverUiState.ipError
    )
}