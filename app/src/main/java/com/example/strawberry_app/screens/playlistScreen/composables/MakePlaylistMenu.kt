package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.formatTime
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData


@Composable
fun MakePlaylistMenu(
    callbacks: PlaylistCallbacks,
    playlistId: Long,
    playlistsData: PlaylistsData,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }
    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Alert(
            action = R.string.playlist_shuffle,
            onConfirm = {
                pendingAction?.invoke()
                showDialog = false
                expanded = false
            },
            onDismiss = { showDialog = false }
        )
    }

    Row(modifier = modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropDownMenuComposable(
            callbacks = callbacks,
            playlistId = playlistId,
            playlistsData = playlistsData,
            selectedTabIndex = selectedTabIndex,
            onConfirm = { action, dialog, expand ->
                pendingAction = action
                if(dialog){
                    showDialog = true
                }else {
                    action.invoke()
                }
                showDialog = dialog
                expanded = expand
            },
            expanded = expanded
        )

        val length = "${stringResource(R.string.playlist_length)}: ${formatTime(playlistsData.playlists[selectedTabIndex].playlistLength)}"
        Text(text = length, color = MaterialTheme.colorScheme.secondary)

        val tracks = "${stringResource(R.string.playlist_song_count)}: ${playlistsData.playlists[selectedTabIndex].playlistSize}"
        Text(text = tracks, color = MaterialTheme.colorScheme.secondary)
    }
}