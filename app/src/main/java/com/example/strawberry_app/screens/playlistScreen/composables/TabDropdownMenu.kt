package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.DropdownText
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData
import com.example.strawberry_app.ui.theme.icons.clear_all
import com.example.strawberry_app.ui.theme.icons.favorite
import com.example.strawberry_app.ui.theme.icons.more_vert
import com.example.strawberry_app.ui.theme.icons.playlist_remove
import com.example.strawberry_app.ui.theme.icons.repeat
import com.example.strawberry_app.ui.theme.icons.shuffle

@Composable
fun TabDropdownMenu(
    actionString: Int,
    callbacks: PlaylistCallbacks,
    expanded: Boolean,
    playlistId: Long,
    playlistsData: PlaylistsData,
    selectedTabIndex: Int,
    onConfirm: (()->Unit, Boolean, Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
){
    var showDialog by remember { mutableStateOf(false) }
    println("repeatmode in tabdropdown: ${playlistsData.playlistState.repeatMode}")
    // If showDialog is true, display RepeatAlert.
    if (showDialog) {
        RepeatAlert(
            currentRepeatMode = playlistsData.playlistState.repeatMode,
            onConfirm = { mode ->
                showDialog = false
                callbacks.sendRepeatMode(mode.toString())
            },
            onDismiss = { showDialog = false }
        )
    }

    Box(
        modifier = modifier
    )
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
            DropdownMenuItem(
                text = { DropdownText(R.string.playlist_favourites) },
                leadingIcon = {
                    Icon(
                        imageVector =  favorite,
                        contentDescription = stringResource(R.string.playlist_favourites)
                    )
                },
                onClick = {
                    onConfirm(
                        { callbacks.sendPlaylistFavourite(
                            playlistId,
                            !playlistsData.playlists[selectedTabIndex].favourite
                        )
                        }, false, false, actionString
                    )
                }
            )
            DropdownMenuItem(
                text = { DropdownText(R.string.playlist_shuffle)},
                leadingIcon = {
                    Icon(
                        imageVector = shuffle,
                        contentDescription = stringResource(R.string.playlist_shuffle)
                    )
                },
                onClick = {
                    onConfirm({ callbacks.shuffleCurrentPlaylist(playlistId) },
                        true, false, R.string.playlist_shuffle)
                }
            )
            DropdownMenuItem(
                text = { DropdownText(R.string.playlist_clear)},
                leadingIcon = {
                    Icon(
                        imageVector = clear_all,
                        contentDescription = stringResource(R.string.playlist_clear)
                    )
                },
                onClick = {
                    onConfirm({ callbacks.clearCurrentPlaylist(playlistId) },
                        true, false, R.string.playlist_clear)}
            )
            DropdownMenuItem(
                text = { DropdownText(R.string.playlist_delete)},
                leadingIcon = {
                    Icon(
                        imageVector = playlist_remove,
                        contentDescription = stringResource(R.string.playlist_delete)
                    )
                },
                onClick = {
                    onConfirm({ callbacks.deleteCurrentPlaylist(playlistId) },
                        true, false, R.string.playlist_delete )}
            )
            DropdownMenuItem(
                text = { DropdownText(R.string.playlist_repeat)},
                leadingIcon = {
                    Icon(
                        imageVector = repeat,
                        contentDescription = stringResource(R.string.playlist_repeat)
                    )
                },
                onClick = {
                    onConfirm({ },false, false, R.string.playlist_repeat)
                    showDialog = true
                }
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
        selectedTabIndex = 0,
        onConfirm = {_,_,_,_ ->},
        modifier = Modifier
    )
}