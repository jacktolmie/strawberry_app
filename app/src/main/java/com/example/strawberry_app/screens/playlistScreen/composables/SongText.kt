package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SongText(
    color: Color,
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
        color = color,
        fontWeight = fontWeight
    )
}