package com.example.strawberry_app.screens.settingsScreen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.screens.settingsScreen.composables.ConnStateSmall
import com.example.strawberry_app.screens.settingsScreen.composables.SmallScreenBtns
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxIpVert
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPasswordVert
import com.example.strawberry_app.screens.settingsScreen.composables.TextboxPortVert
import com.example.strawberry_app.server.SettingsUiState

@Composable
fun SettingsSmallPortraitScreen(
    serverUiState: SettingsUiState,
    connectionState: ConnectionState,
    callbacks: SettingsCallbacks,
    hasNetwork: Boolean,
    settingsGuiData: SettingsGuiData,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .statusBarsPadding()
        .fillMaxSize()
        .navigationBarsPadding()
        .padding(10.dp)
        .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TextBox(
            color = MaterialTheme.colorScheme.onSurface,
            textRes = R.string.settings_title,
            textStyle = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextboxIpVert(serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        TextboxPortVert(serverUiState, callbacks)

        Spacer(modifier = Modifier.height(10.dp))

        TextboxPasswordVert(serverUiState, callbacks)

        SmallScreenBtns(serverUiState, callbacks, connectionState, hasNetwork)

        ConnStateSmall(settingsGuiData)
    }
}

@Preview
@Composable
fun SettingsSmallPortraitPreview(){
    SettingsSmallPortraitScreen(
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
            hasNetwork = true,
            settingsGuiData = SettingsGuiData()
    )
}