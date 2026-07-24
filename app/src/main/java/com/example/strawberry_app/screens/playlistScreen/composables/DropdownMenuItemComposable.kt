package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.strawberry_app.screens.DropdownText

@Composable
fun DropdownMenuItemComposable(
    iconImage: ImageVector,
    onConfirm: ()->Unit,
    playlistName: String = "",
    text: Int,
) {
    DropdownMenuItem(
        text = { DropdownText(text) },
        leadingIcon = {
            Icon(
                imageVector = iconImage,
                contentDescription = "${stringResource(text)} $playlistName"
            )
        },
        onClick = onConfirm
    )
}