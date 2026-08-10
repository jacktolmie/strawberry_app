package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.TextFieldBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun TextboxPortHoriz(
    settingsUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        TextBox(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textRes = R.string.settings_port,
            textStyle = MaterialTheme.typography.bodyLarge)

        TextFieldBox(
            valueField = settingsUiState.port,
            onValue = callbacks.onPortChanged,
            label = R.string.settings_port_range,
            keyboard = KeyboardType.Number,
            error = settingsUiState.portError,
            modifier = modifier
        )
    }
}

@Composable
fun TextboxPortVert(
    settingsUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ){
        TextBox(
            modifier = Modifier.weight(.75F),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.End,
            textRes = R.string.settings_port,
            textStyle = MaterialTheme.typography.bodySmall)

        TextFieldBox(
            modifier = Modifier.weight(2.25F),
            valueField = settingsUiState.port,
            onValue = callbacks.onPortChanged,
            label = R.string.settings_port_range,
            keyboard = KeyboardType.Number,
            error = settingsUiState.portError
        )
    }
}

@Preview
@Composable
fun TextBoxPortPreview(){
    val settingsUiState = SettingsUiState()
    val callbacks = SettingsCallbacks()

    Column {
        TextboxPortHoriz(
            settingsUiState = settingsUiState,
            callbacks = callbacks
        )

        TextboxPortVert(
            settingsUiState = settingsUiState,
            callbacks = callbacks
        )
    }
}