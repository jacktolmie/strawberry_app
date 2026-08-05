package com.example.strawberry_app.screens.playlistScreen.composables

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistScreenState
import com.example.strawberry_app.ui.theme.icons.delete
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import java.io.File

@Composable
fun CurrentPlaylist(
    albumArtCollection: Map<String, File?>,
    callbacks: PlaylistCallbacks,
    playlist: List<SongWithPosition>,
    playlistScreenState: PlaylistScreenState,
    modifier: Modifier = Modifier
){
    var reorderablePlaylist by remember(playlist) { mutableStateOf(playlist) }
    val lazyListState = rememberLazyListState()

    // Variable to hold index of song being moved on playlist to send to server.
    var toIndex by remember { mutableLongStateOf(0)}

    val reorderablePlaylistState = rememberReorderableLazyListState(lazyListState) { from, to ->
        reorderablePlaylist = reorderablePlaylist.toMutableList().apply {
            add(to.index, removeAt(from.index))
            toIndex = to.index.toLong()
        }
    }
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
            state = lazyListState,
            contentPadding = paddingValues,
            modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            items(reorderablePlaylist, key = { it.position }) { song ->
                ReorderableItem(reorderablePlaylistState, key = song.position) { isDragging ->
                    val elevation by animateDpAsState( if (isDragging) 4.dp else 0.dp)

                    if (!albumArtCollection.contains(song.coverImage)){
                        callbacks.getAlbumArtFile(song.coverImage)
                    }
                    println("currentplaylist currentsongindex: ${playlistScreenState.playlistsData.playlistState.currentSongIndex} and song position: ${song.position} ")
                    Surface(shadowElevation = elevation) {
                        SongItem(
                            callbacks = callbacks,
                            playlistId = playlistScreenState.playlistsData.playlistState.currentPlaylist,
                            playlistScreenState = playlistScreenState,
                            song = song,
                            imageArt = albumArtCollection[song.coverImage],
                            // If current and active playlist are the same, and the song position
                            // matches the active playlist index, highlight current song.
                            isPlaying = if(
                                playlistScreenState.playlistsData.playlistState.activePlaylist ==
                                playlistScreenState.playlistsData.playlistState.currentPlaylist &&
                                playlistScreenState.playlistsData.playlistState.currentSongIndex ==
                                song.position
                            ) callbacks.isCurrentPlaying(song.id) else false,
                            modifier = Modifier.draggableHandle(
                                onDragStopped = {
                                    callbacks.sendCurrentChangedPlaylist(
                                    playlistScreenState.playlistsData.playlistState.currentPlaylist,
                                    toIndex, song.position
                                    )
                                    callbacks.setDragIcon(null)
                                }
                            )
                        )
                    }

                    HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

//@Preview
@Preview(name = "Phone Portrait", showBackground = true, widthDp = 360, heightDp = 800)
@Preview(name = "Phone Landscape", showBackground = true, widthDp = 800, heightDp = 360)
@Preview(name = "Tablet", showBackground = true, widthDp = 1280, heightDp = 800)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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