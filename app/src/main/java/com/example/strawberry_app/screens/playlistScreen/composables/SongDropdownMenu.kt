package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.ui.theme.icons.check_box
import com.example.strawberry_app.ui.theme.icons.check_box_outline_blank
import com.example.strawberry_app.ui.theme.icons.delete
import com.example.strawberry_app.ui.theme.icons.more_horiz

@Composable
fun SongDropdownMenu(
    expanded: Boolean,
    onConfirm: (expanded: Boolean) -> Unit,
    onChecked: (expandAllRows: Boolean) -> Unit,
    songTitle: String,
    modifier: Modifier = Modifier
){
    var isChecked by remember { mutableStateOf( false) }
    var expanded by remember { mutableStateOf( false)}

    Box(modifier = modifier)
    {
        IconButton( onClick = { expanded = true} ) {
            Icon( modifier = Modifier.padding(start = 20.dp), imageVector = more_horiz, contentDescription = "Song item dropdown" )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            DropdownMenuItemComposable(
                iconImage = delete,
                isIconVisible = true,
                itemName = songTitle,
                onConfirm = { onConfirm(false) },
                text = R.string.playlist_delete_song
            )
            DropdownMenuItemComposable(
                iconImage = if (isChecked) check_box else check_box_outline_blank,
                onConfirm = {isChecked = !isChecked}, // Needs to have delete icon disappear if selected
                itemName = "",
                text = R.string.playlist_multi_select
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun SongDropdownPreview(){
    SongDropdownMenu(
        expanded = true,
        onConfirm = {},
        onChecked = {},
        songTitle = "Song Name"
    )
}