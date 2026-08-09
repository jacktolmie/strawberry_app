package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.playlistScreen.RepeatModeValues

@Composable
fun RepeatAlert(
    currentRepeatMode: RepeatModeValues,
    onConfirm: (RepeatModeValues) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
){
    var selected by remember { mutableStateOf(currentRepeatMode) }

    AlertDialog(
        modifier = modifier,
        confirmButton = {
            TextButton(onClick = { onConfirm(selected) }) {
                Text(text = stringResource(R.string.playlist_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }

        },
        onDismissRequest = onDismiss,
        text = {
            RepeatRadioBtn(
                selected = selected,
                onOptionSelected = { selected = it }
            )
        },
        title = { Text(text = stringResource(R.string.playlist_repeat_choose))}
        )
}

@Composable
fun RepeatRadioBtn(
    selected: RepeatModeValues,
    onOptionSelected: (RepeatModeValues) -> Unit
){
    val repeatOptions = listOf(
        RepeatModeValues.OFF,
        RepeatModeValues.TRACK,
        RepeatModeValues.ALBUM,
        RepeatModeValues.PLAYLIST,
        RepeatModeValues.STOP,
        RepeatModeValues.INTRO
    )

    Column(
        modifier = Modifier.selectableGroup()
    ) {
        repeatOptions.forEach { option ->
            Row( modifier = Modifier
                .selectable(
                    selected = (option == selected),
                    onClick = {onOptionSelected(option)},
                    role = Role.RadioButton
                ),
                verticalAlignment = Alignment.CenterVertically
            ){
                RadioButton(
                    selected = (option == selected),
                    onClick = null
                )
                Text(text = RepeatModeValues.toString(option))
            }
        }
    }
}

@Preview
@Composable
fun RepeatRadioBtnPreview(){
    RepeatRadioBtn(
        selected = RepeatModeValues.OFF,
        onOptionSelected = {}
    )
}