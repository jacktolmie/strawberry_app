package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R

@Composable
fun Alert(
    action: Int,
    question: Int,
    playlistName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title ={ DropdownTitle(question) },
        text = { Text(text = "${stringResource(action)} $playlistName") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.playlist_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
@Preview
fun AlertPreview(){
    Alert(
        action = R.string.playlist_shuffle,
        question = R.string.playlist_question,
        playlistName = "Playlist 2100",
        onConfirm = {},
        onDismiss = {}
    )
}
