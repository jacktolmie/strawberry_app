package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.composables.TextFieldBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxPassword(
    settingsUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        TextFieldBox(
            valueField = settingsUiState.password,
            label = R.string.settings_password,
            onValue = callbacks.onPasswordChanged,
            keyboard = KeyboardType.Text,
            placeholder = { stringResource(R.string.settings_password) },
        )
    }
}

@Preview
@Composable
fun TextboxPasswordPreview(){
    TextboxPassword(
        settingsUiState = SettingsUiState(),
        callbacks = SettingsCallbacks(),
        modifier = Modifier.background(Color.White)
    )
}