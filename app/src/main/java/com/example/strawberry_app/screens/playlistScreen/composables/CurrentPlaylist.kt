package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.ui.theme.icons.delete
import java.io.File

@Composable
fun CurrentPlaylist(
    albumArtCollection: Map<String, File?>,
    callbacks: PlaylistCallbacks,
    playlist: List<SongWithPosition>,
    playlistScreenState: PlaylistScreenState,
    modifier: Modifier = Modifier
){
    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = playlistScreenState.isInSelectedMode,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(onClick = {
                    callbacks.deleteSelectedSongs(
                        playlistScreenState.playlistsData.playlistState.currentPlaylist,
                    )
                }) {
                    Icon(
                        imageVector = delete,
                        contentDescription = stringResource(R.string.playlist_delete_song))
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = modifier
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
                    playlistScreenState = playlistScreenState,
                    position = song.position,
                    song = song,
                    imageArt = albumArtCollection[song.coverImage],
                    // If current and active playlist are the same, and the song position
                    // matches the active playlist index, highlight current song.
                    isPlaying = if(
                        playlistScreenState.playlistsData.playlistState.activePlaylist ==
                        playlistScreenState.playlistsData.playlistState.currentPlaylist &&
                        playlistScreenState.playlistsData.playlistState.currentSongIndex ==
                        song.position
                    ) callbacks.isCurrentPlaying(song.id) else false

                )
                HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.primary)
            }
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