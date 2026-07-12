package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.TextFieldBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxPort(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    modifier: Modifier = Modifier
){
    TextBox(R.string.settings_port, MaterialTheme.typography.bodyLarge)

    TextFieldBox(
        valueField = serverUiState.port,
        onValue = callbacks.onPortChanged,
        label = R.string.settings_port_range,
        keyboard = KeyboardType.Number,
        error = serverUiState.portError,
        modifier = modifier
    )
}