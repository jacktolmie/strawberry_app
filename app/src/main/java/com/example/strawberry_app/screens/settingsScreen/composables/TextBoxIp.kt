package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextBox
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
        TextBox(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textRes = R.string.settings_ip,
            textStyle = MaterialTheme.typography.bodyLarge)

        TextFieldBox(
            valueField = settingsUiState.ip,
            onValue = callbacks.onIpChanged,
            label = R.string.settings_ip_info,
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
        TextBox(
            modifier = Modifier.weight(.75F),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textRes = R.string.settings_ip,
            textAlign = TextAlign.End,
            textStyle = MaterialTheme.typography.bodySmall)

        TextFieldBox(
            modifier = Modifier.weight(2.25F),
            valueField = settingsUiState.ip,
            onValue = callbacks.onIpChanged,
            label = R.string.settings_ip_info,
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
            callbacks = callbacks
        )

        TextboxIpVert(
            settingsUiState = settingsUiState,
            callbacks = callbacks
        )
    }

}