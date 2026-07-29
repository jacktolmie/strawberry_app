package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import java.io.File

@Composable
fun CurrentPlaylist(
    albumArtCollection: Map<String, File?>,
    callbacks: PlaylistCallbacks,
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
            if (!albumArtCollection.contains(song.coverImage)){
                callbacks.getAlbumArtFile(song.coverImage)
            }

            SongItem(
                callbacks = callbacks,
                playlistId = playlistScreenState.playlistsData.playlistState.currentPlaylist,
                position = song.position,
                song = song,
                imageArt = albumArtCollection[song.coverImage],
                // If current and active playlist are the same, highlight current song.
                isPlaying = if(
                    playlistScreenState.playlistsData.playlistState.activePlaylist ==
                    playlistScreenState.playlistsData.playlistState.currentPlaylist
                    ) callbacks.isCurrentPlaying(song.id) else false

            )
            HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview
@Composable
fun CurrentPlaylistPreview(){
    CurrentPlaylist(
        albumArtCollection = emptyMap(),
        callbacks = PlaylistCallbacks(),
        playlist = sampleSongList(),
        playlistScreenState = PlaylistScreenState(),
        modifier = Modifier
    )
}