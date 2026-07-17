package com.example.strawberry_app.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


// Text box for dropdown menu text
@Composable
fun DropdownText(
    textRes: Int
){
    Text(
        text = stringResource(textRes),
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

// Text box for songitem.kt text
@Composable
fun SongText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal
){
    Text(
        modifier = modifier,
        text = text,
        overflow = TextOverflow.Ellipsis,
        style = style,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = fontWeight
    )
}

@Composable
fun TextBox(
    textRes: Int,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(textRes),
        style = textStyle,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun TextBox(
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
){
    Text(
        text = text,
        style = textStyle,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun TextFieldBox(
    valueField: String,
    onValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: Int? = null,
    keyboard: KeyboardType = KeyboardType.Text,
    error: Int? = null
){
    TextField(modifier = modifier
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