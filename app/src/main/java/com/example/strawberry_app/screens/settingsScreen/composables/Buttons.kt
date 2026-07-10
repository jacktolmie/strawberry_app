package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.ConnectionState.Connected
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
    connectionState: ConnectionState
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        SaveButton(callback = callbacks.onSaveClicked, enableBtn = serverUiState.enableSaveButton)
        Spacer(modifier = Modifier.width(8.dp))
        CancelButton(callbacks.onCancelClicked )
    }

    Spacer(modifier = Modifier.height(10.dp))
    ConnectButton(callbacks = callbacks, connectionState)
}

@Composable
fun SmallScreenBtns(
    serverUiState: SettingsUiState,
    callbacks: SettingsCallbacks,
    connectionState: ConnectionState
){
//    val isConnected = connectionState == Connected
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Button(
            onClick = { callbacks.onSaveClicked() },
            enabled = serverUiState.enableSaveButton
        )
        {
            TextBox(R.string.settings_save, MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { callbacks.onCancelClicked() }
        )
        {
            TextBox(R.string.cancel, MaterialTheme.typography.bodySmall)
        }
    }

    Spacer(modifier = Modifier.height(10.dp))


    Button(
        onClick ={
            callbacks.onDisconnectClicked()
        }
    )
    {
        Text(
            modifier = Modifier.widthIn(min = 80.dp),
            textAlign = TextAlign.Center,
            text = "Testing 123"
//            text = if(isConnected) "Disconnect" else "Connect"
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
        ButtonText(R.string.cancel, MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ConnectButton(
    callbacks: SettingsCallbacks,
    connectionState: ConnectionState
){
    Button(
        onClick ={
            if (isConnected(connectionState) == "Connect") callbacks.onConnectClicked() else callbacks.onDisconnectClicked()
        }
    )
    {
        ButtonText( text = isConnected(connectionState), textStyle = MaterialTheme.typography.bodySmall )
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
fun ButtonsPreview(){
    MedLrgScreenBtns(
        SettingsUiState(),
        SettingsCallbacks(),
        connectionState = ConnectionState.Connected
    )
}