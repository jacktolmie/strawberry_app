package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Box
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
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData
import com.example.strawberry_app.screens.playlistScreen.RepeatModeValues
import com.example.strawberry_app.screens.playlistScreen.ShuffleModeValues
import com.example.strawberry_app.ui.theme.icons.clear_all
import com.example.strawberry_app.ui.theme.icons.delete_sweep
import com.example.strawberry_app.ui.theme.icons.drive_file_rename
import com.example.strawberry_app.ui.theme.icons.favorite
import com.example.strawberry_app.ui.theme.icons.more_vert
import com.example.strawberry_app.ui.theme.icons.playlist_remove
import com.example.strawberry_app.ui.theme.icons.repeat
import com.example.strawberry_app.ui.theme.icons.shuffle
import com.example.strawberry_app.ui.theme.icons.stack_off

@Composable
fun TabDropdownMenu(
    actionString: Int,
    callbacks: PlaylistCallbacks,
    expanded: Boolean,
    playlistId: Long,
    playlistsData: PlaylistsData,
    playlistName: String,
    selectedTabIndex: Int,
    onConfirm: (()->Unit, Boolean, Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
){
    var showRenameDialog by remember { mutableStateOf(false)}
    var showRepeatDialog by remember { mutableStateOf(false) }
    var showShuffleDialog by remember { mutableStateOf(false)}
    var newPlaylistName by remember(playlistName) { mutableStateOf(playlistName)}

    // If showRepeatDialog is true, display RepeatAlert.
    if (showRepeatDialog) {
        println("tabdrop repeat mode: ${RepeatModeValues.fromString(playlistsData.playlistState.repeatMode)}")
        RepeatAlert(
            currentRepeatMode = RepeatModeValues.fromString(playlistsData.playlistState.repeatMode),
            onConfirm = { mode ->
                showRepeatDialog = false
                callbacks.sendRepeatMode(mode.toString())
            },
            onDismiss = { showRepeatDialog = false }
        )
    }

    // If showRenameDialog is true, display RenameAlert
    if (showRenameDialog) {
        RenameAlert(
            name = newPlaylistName,
            onConfirm = { name ->
                showRenameDialog = false
                callbacks.renameCurrentPlaylist(playlistId, name)
            },
            onDismiss = { showRenameDialog = false }
        )
    }

    if (showShuffleDialog){
        ShuffleAlert(
            currentShuffleMode = ShuffleModeValues.fromString(playlistsData.playlistState.shuffleMode),
            onConfirm = {mode ->
                showShuffleDialog = false
                callbacks.sendShuffleMode(mode.toString())
            },
            onDismiss = { showShuffleDialog = false }
        )
    }

    Box( modifier = modifier )
    {
        IconButton(onClick = {
            onConfirm({}, false, !expanded, actionString)
        }){
            Icon(
                imageVector = more_vert,
                contentDescription = "Menu"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onConfirm({}, false, false, actionString)}
        ) {
            DropdownMenuItemComposable(
                text = R.string.playlist_favourites,
                iconImage = favorite,
                onConfirm = { onConfirm( { callbacks.sendPlaylistFavourite(
                            playlistId,
                            !playlistsData.playlists[selectedTabIndex].favourite
                        )}, false, false, actionString)
                },
                itemName = playlistName
            )
            DropdownMenuItemComposable(
                text = R.string.playlist_repeat,
                iconImage = repeat,
                onConfirm = { onConfirm({ }, false, false, R.string.playlist_repeat)
                    showRepeatDialog = true
                },
                itemName = playlistName
            )
            DropdownMenuItemComposable(
                text = R.string.playlist_shuffle,
                iconImage = shuffle,
                onConfirm = { onConfirm({ }, false, false, R.string.playlist_shuffle)
                    showShuffleDialog = true
                },
                itemName = playlistName
            )

            DropdownMenuItemComposable(
                text = R.string.playlist_clear,
                iconImage = clear_all,
                onConfirm = { onConfirm({ callbacks.clearCurrentPlaylist(playlistId) },
                        true, false, R.string.playlist_clear)
                },
                itemName = playlistName
            )
            DropdownMenuItemComposable(
                text = R.string.playlist_delete,
                iconImage = playlist_remove,
                onConfirm = { onConfirm({ callbacks.deleteCurrentPlaylist(playlistId) },
                        true, false, R.string.playlist_delete )
                    },
                itemName = playlistName
            )
            DropdownMenuItemComposable(
                text = R.string.playlist_remove_duplicates,
                iconImage = stack_off,
                onConfirm = { onConfirm ({ callbacks.removeDuplicatesInPlaylist(playlistId) } ,
                        true, false, R.string.playlist_remove_duplicates_from )
                    },
                itemName = playlistName
            )
            DropdownMenuItemComposable(
                text = R.string.playlist_remove_unavailable,
                iconImage = delete_sweep,
                onConfirm = { onConfirm({ callbacks.removeUnavailableSongs(playlistId) },
                    true, false, R.string.playlist_remove_unavailable_from)
                },
                itemName = playlistName
            )
            DropdownMenuItemComposable(
                text = R.string.playlist_rename,
                iconImage = drive_file_rename,
                onConfirm = { onConfirm({},
                    false, false, R.string.playlist_rename)
                    showRenameDialog = true
                },
                itemName = playlistName
            )
        }
    }
}

@Preview
@Composable
fun TabDropdownPreview(){
    TabDropdownMenu(
        actionString = R.string.playlist_shuffle,
        callbacks = PlaylistCallbacks(),
        expanded = true,
        playlistId = 1L,
        playlistsData = PlaylistsData(),
        playlistName = "Playlist 1000",
        selectedTabIndex = 0,
        onConfirm = {_,_,_,_ ->},
        modifier = Modifier
    )
}