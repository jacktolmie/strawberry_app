package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState

@Composable
fun CurrentPlaylist(
    playlist: List<SongWithPosition>,
    playlistScreenState: PlaylistScreenState,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)
        .background(MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        items(playlist){ song ->
            SongItem(
                song = song,
                imageArt = playlistScreenState.albumArtFile,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(48.dp) // Absolute size anchor for a clean row
                    .clip(RoundedCornerShape(4.dp))
            )
            HorizontalDivider(thickness = 5.dp)
        }
    }
}