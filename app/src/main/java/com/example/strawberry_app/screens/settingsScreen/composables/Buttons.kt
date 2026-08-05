package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.visible
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
    Row(modifier = modifier
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
    ConnectButton(callbacks = callbacks,
        connectionState = connectionState,
        hasNetwork = hasNetwork
    )
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
    callback: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { callback() },
        modifier = modifier
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
    hasNetwork: Boolean,
    modifier: Modifier = Modifier
){
    Button(
        modifier = modifier,
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
    enableBtn: Boolean,
    modifier: Modifier = Modifier
){
    Button(
        onClick = { callback() },
        enabled = enableBtn,
        modifier = modifier
    )
    {
        ButtonText(R.string.settings_save, MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun ButtonsPreview(){
    MedLrgScreenBtns(
        serverUiState = SettingsUiState(),
        callbacks = SettingsCallbacks(),
        hasNetwork = true,
        connectionState = Connected
    )
}