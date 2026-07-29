package com.example.strawberry_app.screens.playlistScreen.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.SongImageComposable
import com.example.strawberry_app.screens.formatTime
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.ui.theme.icons.more_horiz
import java.io.File

@Composable
fun SongItem(
    callbacks: PlaylistCallbacks,
    playlistId: Long,
    position: Long,
    song: SongWithPosition,
    imageArt: File? = null,
    isPlaying: Boolean = false

) {
    var showDropdown by remember { mutableStateOf( false) }
    var expandAllRows by remember { mutableStateOf( false) }
//    var expanded by remember { mutableStateOf(false) }

//    if (showDropdown){
//        SongDropdownMenu(
//            expanded = true,
//            onConfirm = {},
//            onChecked = { expandAllRows },
//            songTitle = song.title
//        )
//    }

    Row(
        modifier = Modifier
            .combinedClickable(
                onClick = {}, // Finish this if needed single click
                onDoubleClick = {
                    callbacks.sendActivePlaylistSong(playlistId, position)
                },
                onLongClick = {} // Finish this for long press to move song on playlist.
            )
            .background(
                color =
                    if ( isPlaying ) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
            )
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

    ) {
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
                    text = song.title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold
                )
                SongText(
                    text = formatTime(song.length),
                    style = MaterialTheme.typography.bodyMedium
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
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )

                SongDropdownMenu(
                    expanded = true,
                    onConfirm = {},
                    onChecked = { expandAllRows },
                    songTitle = song.title
                )

//                Icon(modifier = Modifier
//                    .clickable(
//                        onClick = {
//                            showDropdown = true
//                        }
//                    ),
//                    imageVector = more_horiz, contentDescription = "Song item dropdown"
//                )

                // We leave the right side blank here so it aligns perfectly
                // under the clean layout, or you could place an explicit 'IconButton' here later.
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
        position = 1L,
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