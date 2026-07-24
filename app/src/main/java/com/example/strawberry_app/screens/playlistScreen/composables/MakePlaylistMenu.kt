package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.TextBox
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
    var actionString by remember { mutableIntStateOf(-1) }
    var expanded by remember { mutableStateOf(false) }
    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var showAlert by remember { mutableStateOf(false) }

    if (showAlert) {
        Alert(
            action = actionString,
            question = R.string.playlist_question,
            onConfirm = {
                pendingAction?.invoke()
                showAlert = false
                expanded = false
            },
            onDismiss = { showAlert = false }
        )
    }

    Row(modifier = modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TabDropdownMenu(
            actionString = actionString,
            callbacks = callbacks,
            playlistId = playlistId,
            playlistsData = playlistsData,
            selectedTabIndex = selectedTabIndex,
            onConfirm = { action, dialog, expand, actionText ->
                pendingAction = action
                if(dialog){
                    showAlert = true
                }else {
                    action.invoke()
                }
                showAlert = dialog
                expanded = expand
                actionString =actionText
            },
            expanded = expanded
        )

        val length = "${stringResource(R.string.playlist_length)}: ${formatTime(playlistsData.playlists[selectedTabIndex].playlistLength)}"
        TextBox(text = length, textStyle = MaterialTheme.typography.bodyMedium)

        val tracks = "${stringResource(R.string.playlist_song_count)}: ${playlistsData.playlists[selectedTabIndex].playlistSize}"
        TextBox(text = tracks, textStyle = MaterialTheme.typography.bodyMedium)
    }
}