package com.example.strawberry_app.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R

@Composable
fun TextBox(
    color: Color,
    textRes: Int,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1,
){
    Text(
        color = color,
        maxLines = maxLines,
        text = stringResource(textRes),
        textAlign = textAlign,
        style = textStyle,
        modifier = modifier
    )
}

@Composable
fun TextBox(
    color: Color,
    text: String,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
){
    Text(
        color = color,
        maxLines = maxLines,
        text = text,
        style = textStyle,
        modifier = modifier
    )
}

@Composable
fun TextFieldBox(
    valueField: String,
    onValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit),
    label: Int? = null,
    keyboard: KeyboardType = KeyboardType.Decimal,
    error: Int? = null
){
    OutlinedTextField(modifier = modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
        placeholder = placeholder,
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
fun TextPreview(){
    TextFieldBox(
        valueField = "Testing Outlined",
        onValue = {},
        label = R.string.settings_ip,
        modifier = Modifier.background(Color.White),
        placeholder = { stringResource(R.string.settings_ip_info) },
    )
}