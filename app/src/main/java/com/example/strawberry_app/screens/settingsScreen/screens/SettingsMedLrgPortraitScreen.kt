package com.example.strawberry_app.screens.settingsScreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.network.ConnectionState.Connected
import com.example.strawberry_app.network.SettingsGuiData
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.screens.settingsScreen.SettingsScreenState
import com.example.strawberry_app.screens.settingsScreen.composables.ConnStateMedLrg
import com.example.strawberry_app.screens.settingsScreen.composables.MedLrgScreenBtns
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxIpHoriz
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPasswordHoriz
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPortHoriz
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun SettingsMedLrgPortraitScreen(
    callbacks: SettingsCallbacks,
    state: SettingsScreenState,
    isPortrait: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .statusBarsPadding()
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(10.dp)
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        TextBox(
            color = MaterialTheme.colorScheme.onSurface,
            textRes = R.string.settings_title,
            textStyle = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextboxIpHoriz(state.serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        TextboxPortHoriz(state.serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        TextboxPasswordHoriz(state.serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        MedLrgScreenBtns(state.serverUiState, callbacks, state.connectionState, state.hasNetwork)

        ConnStateMedLrg(state.settingsGuiData)

    }
}

@Preview
@Composable
fun SettingsMedLrgPortraitPreview(){
    SettingsMedLrgPortraitScreen(
        state = SettingsScreenState(
            serverUiState = SettingsUiState(
                ip = "192.168.1.201",
                port = "5000",
                password = "",
                hasChanged = false,
                isPortValid = true
            ),
            connectionState = Connected,
            hasNetwork = true,
            settingsGuiData = SettingsGuiData()
        ),
        callbacks = SettingsCallbacks(
            onIpChanged = {},
            onPortChanged = {},
            onPasswordChanged = {},
            onSaveClicked = {},
            onCancelClicked = {},
            onDisconnectClicked = {},
            onConnectClicked = {}
        ),
        isPortrait = true
    )
}