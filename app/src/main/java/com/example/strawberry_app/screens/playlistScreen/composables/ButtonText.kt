package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ButtonText(
    textRes: Int,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(textRes),
        style = textStyle,
        modifier = modifier
    )
}

@Composable
fun ButtonText(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier.widthIn(min = 80.dp),
        text = text,
        style = textStyle,
        textAlign = TextAlign.Center
    )
}