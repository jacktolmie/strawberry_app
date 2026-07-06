package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextBox(textRes: Int, textStyle: TextStyle){
    Text(
        text = stringResource(textRes),
        style = textStyle,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun TextBox(text: String, textStyle: TextStyle){
    Text(
        text = text,
        style = textStyle,
        color = MaterialTheme.colorScheme.primary
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

@Composable
fun ButtonText(
    textRes: Int,
    textStyle: TextStyle
){
    Text(
        text = stringResource(textRes),
        style = textStyle
    )
}

@Composable
fun ButtonText(
    text: String,
    textStyle: TextStyle
){
    Text(
        modifier = Modifier.widthIn(min = 80.dp),
        text = text,
        style = textStyle,
        textAlign = TextAlign.Center
    )
}