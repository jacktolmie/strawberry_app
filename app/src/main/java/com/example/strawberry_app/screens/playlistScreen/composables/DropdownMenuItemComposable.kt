package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.ui.theme.icons.playlist_remove

@Composable
fun DropdownMenuItemComposable(
    iconImage: ImageVector,
    onConfirm: ()->Unit,
    playlistName: String,
    text: Int,
    modifier: Modifier = Modifier
) {
    DropdownMenuItem(
        modifier = modifier,
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

@Composable
@Preview
fun DropdownPreview(){
    DropdownMenuItemComposable(
        iconImage =playlist_remove,
        onConfirm = {},
        playlistName = "Playlist 1",
        text = R.string.playlist_delete,
        Modifier.background(Color.White)
    )
}