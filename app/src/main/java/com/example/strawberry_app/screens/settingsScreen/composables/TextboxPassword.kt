package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.TextFieldBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxPassword(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks
){
    TextBox(R.string.settings_password, MaterialTheme.typography.bodyLarge)

    TextFieldBox(serverUiState.password, callbacks.onPasswordChanged)

}