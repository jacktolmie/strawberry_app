package com.example.strawberry_app.screens.settingsScreen

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
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.ConnectionState.Connected
import com.example.strawberry_app.network.SettingsGuiData
import com.example.strawberry_app.screens.settingsScreen.composables.ConnStateMedLrg
import com.example.strawberry_app.screens.settingsScreen.composables.MedLrgScreenBtns
import com.example.strawberry_app.screens.settingsScreen.composables.TextBox
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxIp
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPassword
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPort
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun SettingsScreen(
    serverUiState: SettingsUiState,
    connectionState: ConnectionState,
    callbacks: SettingsCallbacks,
    settingsGuiData: SettingsGuiData
) {

    Column(modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth()
        .navigationBarsPadding()
        .padding(10.dp)
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        TextBox(R.string.settings_title, MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(10.dp))

        TextboxIp(serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        TextboxPort(serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        TextboxPassword(serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        MedLrgScreenBtns(serverUiState, callbacks, connectionState)

        ConnStateMedLrg(settingsGuiData)

    }
}



@Preview
@Composable
fun SettingsPreview(){
    SettingsScreen(
        serverUiState = SettingsUiState(
            ip = "192.168.1.201",
            port = "5000",
            password = "",
            hasChanged = false,
            isPortValid = true
        ),

        connectionState = Connected,
        callbacks = SettingsCallbacks(
            onIpChanged = {},
            onPortChanged = {},
            onPasswordChanged = {},
            onSaveClicked = {},
            onCancelClicked = {},
            onDisconnectClicked = {},
            onConnectClicked = {}
        ),
        SettingsGuiData()
    )
}