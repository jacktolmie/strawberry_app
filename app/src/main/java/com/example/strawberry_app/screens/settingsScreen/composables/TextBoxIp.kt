package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextFieldBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxIpHoriz(
    settingsUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    modifier: Modifier = Modifier
){
    Column( modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextFieldBox(
            valueField = settingsUiState.ip,
            onValue = callbacks.onIpChanged,
            label = R.string.settings_ip,
            placeholder = { stringResource(R.string.settings_ip_info) },
            error = settingsUiState.ipError
        )
    }
}

@Composable
fun TextboxIpVert(
    settingsUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    modifier: Modifier = Modifier
){
    Row( modifier = modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ){
        TextFieldBox(
            valueField = settingsUiState.ip,
            onValue = callbacks.onIpChanged,
            placeholder = { stringResource(R.string.settings_ip_info) },
            label = R.string.settings_ip,
            error = settingsUiState.ipError
        )
    }
}

@Preview
@Composable
fun TextBoxIpPreview(){
    val settingsUiState = SettingsUiState()
    val callbacks = SettingsCallbacks()

    Column{
        TextboxIpHoriz(
            settingsUiState = settingsUiState,
            callbacks = callbacks,
            modifier = Modifier.background(Color.White)
        )

        TextboxIpVert(
            settingsUiState = settingsUiState,
            callbacks = callbacks,
            modifier = Modifier.background(Color.White)
        )
    }

}