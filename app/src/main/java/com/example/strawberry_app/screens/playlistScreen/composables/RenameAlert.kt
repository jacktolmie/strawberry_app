package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R

@Composable
fun RenameAlert(
    name: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
){
    val newName = rememberTextFieldState(name)

    AlertDialog(
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = {onConfirm(newName.text.toString()) }) {
                Text(text = stringResource(R.string.playlist_confirm))
            }
        } ,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        onDismissRequest = onDismiss,
        text = {
            TextField(
                state = newName,
                lineLimits = TextFieldLineLimits.SingleLine
            )
        },
        title = { Text(text = "${stringResource(R.string.playlist_rename)} $name?" ) }
    )

}

@Composable
@Preview(showBackground = true)
fun RenamePreview(){
    RenameAlert(
        name = "Playlist Name",
        onConfirm = {},
        onDismiss = {}
    )
}