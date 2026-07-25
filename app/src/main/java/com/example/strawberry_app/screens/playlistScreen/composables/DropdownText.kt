package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun DropdownText(
    textRes: Int,
    playlistName: String = ""
){
    Text(
        text = "${stringResource(textRes)} $playlistName",
        style =  MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface
    )
}

// Text box for title of dropdown menus
@Composable
fun DropdownTitle(
    textRes: Int
){
    Text(
        text = stringResource(textRes),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleMedium
    )
}