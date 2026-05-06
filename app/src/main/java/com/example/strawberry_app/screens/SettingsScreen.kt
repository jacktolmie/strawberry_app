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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.strawberry_app.R
import com.example.strawberry_app.server.ServerViewModel

@Composable
fun SettingsScreen(){

    val viewModel : ServerViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

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
            uiState.ip,
            viewModel::onIpChanged,
            R.string.settings_ip_info,
            error = uiState.ipError
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextBox(R.string.settings_port, MaterialTheme.typography.bodyLarge)

        TextFieldBox(
            uiState.port,
            viewModel::onPortChanged,
            R.string.settings_port_range,
            KeyboardType.Number,
            error = uiState.portError
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextBox(R.string.settings_password, MaterialTheme.typography.bodyLarge)

        TextFieldBox(uiState.password, viewModel::onPasswordChanged )

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)

        )
        {
            TextBox(R.string.settings_connection_status, MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(5.dp))
            // Add colour to the connection status depending on if connected etc.!!!!!!!!!!!!!!!!!!
            TextBox(
                R.string.settings_connected,
                MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Button(
                onClick = { viewModel.save() },
                enabled = uiState.enableSaveButton
            )
            {
                TextBox(R.string.settings_save, MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.cancel() }
            )
            {
                TextBox(R.string.settings_cancel, MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun TextBox(text: Int, textStyle: TextStyle){
    Text(
        text = stringResource(text),
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
    SettingsScreen()
}