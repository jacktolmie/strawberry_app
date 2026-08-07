package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.ConnectionState.Connected
import com.example.strawberry_app.screens.TextBox
import com.example.strawberry_app.screens.playlistScreen.composables.ButtonText
import com.example.strawberry_app.screens.settingsScreen.SettingsCallbacks
import com.example.strawberry_app.server.SettingsUiState


fun isConnected(connectionState: ConnectionState): String{
    return when(connectionState) {
        is Connected -> "Disconnect"
        is ConnectionState.Connecting -> "Disconnect"
        is ConnectionState.Reconnecting -> "Disconnect"
        else -> "Connect"
    }
}

@Composable
fun MedLrgScreenBtns(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    connectionState: ConnectionState,
    hasNetwork: Boolean,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            SaveButton(callback = callbacks.onSaveClicked, enableBtn = serverUiState.enableSaveButton)
            Spacer(modifier = Modifier.width(8.dp))
            CancelButton(callbacks.onCancelClicked )
        }

        Spacer(modifier = Modifier.height(10.dp))
        ConnectButton(
            callbacks = callbacks,
            connectionState = connectionState,
            hasNetwork = hasNetwork
        )
    }
}

@Composable
fun SmallScreenBtns(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    connectionState: ConnectionState,
    hasNetwork: Boolean,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        SaveButton(callback = callbacks.onSaveClicked, enableBtn = serverUiState.enableSaveButton)
        Spacer(modifier = Modifier.width(8.dp))
        CancelButton(callback = callbacks.onCancelClicked)
        Spacer(modifier = Modifier.width(8.dp))
        ConnectButton(
            callbacks = callbacks,
            connectionState = connectionState,
            hasNetwork = hasNetwork
        )
    }
}

@Composable
fun CancelButton(
    callback: () -> Unit
){
    Button(
        onClick = { callback() }
    )
    {
        ButtonText(
            textRes = R.string.cancel,
            textStyle = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ConnectButton(
    callbacks: SettingsCallbacks,
    connectionState: ConnectionState,
    hasNetwork: Boolean
){
    Button(
        onClick ={
            if (isConnected(connectionState) == "Connect")
                callbacks.onConnectClicked() else callbacks.onDisconnectClicked()
        },
        enabled = hasNetwork
    )
    {
        ButtonText(
            text = isConnected(connectionState),
            textStyle = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SaveButton(
    callback: () -> Unit,
    enableBtn: Boolean
){
    Button(
        onClick = { callback() },
        enabled = enableBtn
    )
    {
        ButtonText(R.string.settings_save, MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun MedButtonsPreview(){
    MedLrgScreenBtns(
        serverUiState = SettingsUiState(),
        callbacks = SettingsCallbacks(),
        hasNetwork = true,
        connectionState = Connected,
        modifier = Modifier.background(Color.White)
    )
}

@Preview
@Composable
fun SmButtonsPreview(){
    SmallScreenBtns(
        serverUiState = SettingsUiState(),
        callbacks = SettingsCallbacks(),
        hasNetwork = true,
        connectionState = Connected,
        modifier = Modifier.background(Color.White)
    )
}