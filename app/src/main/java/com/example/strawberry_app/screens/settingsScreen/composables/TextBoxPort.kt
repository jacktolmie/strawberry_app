package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.TextFieldBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxPort(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks
){
    TextBox(R.string.settings_port, MaterialTheme.typography.bodyLarge)

    TextFieldBox(
        serverUiState.port,
        callbacks.onPortChanged,
        R.string.settings_port_range,
        KeyboardType.Number,
        error = serverUiState.portError
    )
}