package com.example.strawberry_app.screens.playlistScreen.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.composables.SongImageComposable
import com.example.strawberry_app.screens.functions.formatTime
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.ui.theme.icons.check_circle
import com.example.strawberry_app.ui.theme.icons.reorder
import java.io.File

@Composable
fun SongItem(
    callbacks: PlaylistCallbacks,
    playlistId: Long,
    playlistScreenState: PlaylistScreenState,
    song: SongWithPosition,
    modifier: Modifier = Modifier,
    imageArt: File? = null,
    isPlaying: Boolean = false

) {
    val isSelected = playlistScreenState.selectedSongs.contains(song.position)
    val showDragIcon = playlistScreenState.dragIconSongId == song.position
    val currentIcon = when {
        showDragIcon -> reorder
        isSelected -> check_circle
        else -> reorder
    }

    Row(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    if (!showDragIcon) {
                        callbacks.toggleSelection(song.position)
                    }
                    callbacks.setDragIcon(null)
                },
                onDoubleClick = {
                    callbacks.remoteSentActive(playlistId, song.position)
                    if (playlistScreenState.isInSelectedMode) callbacks.clearSelectedSongs()
                    callbacks.setDragIcon(null)
                },
                onLongClick = {
                    if(callbacks.isDragIconSong(song.position)){
                        callbacks.setDragIcon(null)
                    }
                    else {
                        callbacks.clearSelectedSongs()
                        callbacks.setDragIcon(song.position)
                    }
                }
            )
            .background(
                color =
                    if ( isPlaying ) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
            )
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = modifier.visible(isSelected || showDragIcon),
            imageVector = currentIcon,
            contentDescription = if (isSelected) stringResource(R.string.playlist_multi_select)
                else stringResource(R.string.playlist_reorder),
        )

        Spacer(modifier = Modifier.padding(5.dp))

        SongImageComposable(
            imageArt = imageArt,
            crossfade = false,
            modifier = Modifier
                .size(48.dp) // Absolute size anchor for a clean row
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Column()
         {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SongText(
                    color = MaterialTheme.colorScheme.onSurface,
                    text = song.title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold
                )
                SongText(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = formatTime(song.length),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text = listOf(song.artist, song.album)
                    .filter { it.isNotEmpty() }
                    .joinToString(separator = " • ")

                SongText(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )

//                SongDropdownMenu(
//                    expanded = true,
//                    onConfirm = {},
//                    onChecked = {}, // expandAllRows },
//                    songTitle = song.title
//                )
            }
        }
    }
}

@Preview
@Composable
fun SongItemPreview() {
    SongItem(
        callbacks = PlaylistCallbacks(),
        playlistId = 1L,
        playlistScreenState = PlaylistScreenState(),
        song = SongWithPosition(
            artist = "The Amazing Band",
            album = "The best of Amazing Band",
            coverImage = "Some cover",
            id = 1,
            length = 1000L,
            playlistId = 1L,
            position = 1L,
            title = "Bird Madness",
            url = "Some url"
        )
    )
}
