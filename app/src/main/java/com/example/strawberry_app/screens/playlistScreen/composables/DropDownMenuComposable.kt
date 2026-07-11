package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData
import com.example.strawberry_app.ui.theme.icons.clear_all
import com.example.strawberry_app.ui.theme.icons.favorite
import com.example.strawberry_app.ui.theme.icons.more_vert
import com.example.strawberry_app.ui.theme.icons.playlist_remove
import com.example.strawberry_app.ui.theme.icons.shuffle

@Composable
fun DropDownMenuComposable(
    actionString: Int,
    callbacks: PlaylistCallbacks,
    expanded: Boolean,
    playlistId: Long,
    playlistsData: PlaylistsData,
    selectedTabIndex: Int,
    onConfirm: (()->Unit, Boolean, Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
){
    Box(){
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
                text = { Text(text = stringResource(R.string.playlist_favourites)) },
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
                text = { Text( text = stringResource(R.string.playlist_shuffle))},
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
                text = { Text( text = stringResource(R.string.playlist_clear))},
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
                text = { Text( text = stringResource(R.string.playlist_delete))},
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
        }
    }
}
