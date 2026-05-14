package com.example.strawberry_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.server.SettingsUiState
import com.skydoves.compose.stability.runtime.TraceRecomposition

@Composable
@TraceRecomposition // Delete later
fun SettingsScreen(
    serverUiState: SettingsUiState,
    connectionState: ConnectionState,

    onIpChanged: (String) -> Unit,
    onPortChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onDisconnectClicked: (Boolean) -> Unit
) {

    val isConnected = connectionState == ConnectionState.Connected

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

        TextBox(R.string.settings_ip, MaterialTheme.typography.bodyLarge)

        TextFieldBox(
            serverUiState.ip,
            onIpChanged,
            R.string.settings_ip_info,
            error = serverUiState.ipError
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextBox(R.string.settings_port, MaterialTheme.typography.bodyLarge)

        TextFieldBox(
            serverUiState.port,
            onPortChanged,
            R.string.settings_port_range,
            KeyboardType.Number,
            error = serverUiState.portError
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextBox(R.string.settings_password, MaterialTheme.typography.bodyLarge)

        TextFieldBox(serverUiState.password, onPasswordChanged )

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Button(
                onClick = { onSaveClicked() },
                enabled = serverUiState.enableSaveButton
            )
            {
                TextBox(R.string.settings_save, MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { onCancelClicked() }
            )
            {
                TextBox(R.string.settings_cancel, MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick ={
                onDisconnectClicked(isConnected)
//                isConnected = !isConnected
            }
        )
        {
            Text(
                modifier = Modifier.widthIn(min = 80.dp),
                textAlign = TextAlign.Center,
                text = if(isConnected) "Disconnect" else "Connect"
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        )
        {
            TextBox(R.string.settings_connection_status, MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.width(5.dp))

            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.circle_24dp_e3e3e3_fill1_wght400_grad0_opsz24),
                contentDescription = null,
                tint = when(connectionState){
                    ConnectionState.Connected -> Color.Green
                    ConnectionState.Connecting -> Color.Yellow
                    ConnectionState.Disconnected -> Color.Red
                    is ConnectionState.Error -> Color.Red
                    is ConnectionState.Reconnecting -> Color.Yellow
                }
            )

            Spacer(modifier = Modifier.width(5.dp))

            TextBox(
                text = when (connectionState) {
                    ConnectionState.Connected ->
                        "Connected"

                    ConnectionState.Connecting ->
                        "Connecting"

                    ConnectionState.Disconnected ->
                        "Disconnected"

                    is ConnectionState.Error ->
                        connectionState.message

                    is ConnectionState.Reconnecting ->
                        "Reconnecting in ${connectionState.time}\nAttempt: ${connectionState.attempt}"
                },
                textStyle = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun TextBox(textRes: Int, textStyle: TextStyle){
    Text(
        text = stringResource(textRes),
        style = textStyle
    )
}

@Composable
fun TextBox(text: String, textStyle: TextStyle){
    Text(
        text = text,
        style = textStyle
    )
}

@Composable
fun TextFieldBox(
    valueField: String,
    onValue: (String) -> Unit,
    label: Int? = null,
    keyboard: KeyboardType = KeyboardType.Text,
    error: Int? = null
){
    TextField(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
        value = valueField,
        onValueChange = onValue,
        label = {
            label?.let { Text(text = stringResource(it)) }
        },
        isError = error != null,
        supportingText = { error?.let { Text(text = stringResource(it))}},
        keyboardOptions = KeyboardOptions(keyboardType = keyboard )
    )
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

        connectionState = ConnectionState.Connected,

        onIpChanged = {},
        onPortChanged = {},
        onPasswordChanged = {},
        onSaveClicked = {},
        onCancelClicked = {}
    ) {}
}