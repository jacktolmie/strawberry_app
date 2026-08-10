package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.TextFieldBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxIp(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    modifier: Modifier = Modifier
){
    TextBox(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textRes = R.string.settings_ip,
        textStyle = MaterialTheme.typography.bodyLarge)

    TextFieldBox(
        valueField = serverUiState.ip,
        onValue = callbacks.onIpChanged,
        label = R.string.settings_ip_info,
        error = serverUiState.ipError,
        modifier = modifier
    )
}