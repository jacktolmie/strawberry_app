package com.example.strawberry_app.screens.playlistScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.screens.playlistScreen.screens.PlaylistMediumScreen

data class PlaylistCallbacks(
    val clearCurrentPlaylist: (id: Long) -> Unit = {},
    val closeCurrentPlaylist: (id: Long) -> Unit = {},
    val deleteCurrentPlaylist: (id: Long) -> Unit = {},
    val onPlaylistSelected: (id: Long) -> Unit = {},
    val removeSongsCurrentPlaylist: (id: Long, songsList: List<Long>) -> Unit = { _,_ ->},
    val removeDuplicatesInPlaylist: (id: Long) -> Unit = {},
    val renameCurrentPlaylist: (id: Long, name: String) -> Unit = { _,_ ->},
    val sendActivePlaylistSong: (id: Long, songIndex: Long) -> Unit = { _,_ ->},
    val sendAllPlaylists: (playlists: List<Playlist>) -> Unit = {},
    val sendCurrentPlaylist: (id: Long, playlist: Playlist) -> Unit = { _,_ ->},
    val sendPlaylistFavourite: (id: Long, favourite: Boolean) -> Unit = { _,_ ->},
    val setCurrentPlaylist: (id: Long) -> Unit = {},
    val shuffleAllPlaylists: () -> Unit = {},
    val shuffleCurrentPlaylist: (id: Long) -> Unit = {}
    )

@Composable
fun PlaylistRoute(
    playlistViewModel: PlaylistViewModel = hiltViewModel()
){
    val playlistsData by playlistViewModel.playlistsData.collectAsStateWithLifecycle()

    val callbacks = PlaylistCallbacks(
        clearCurrentPlaylist = playlistViewModel::clearCurrentPlaylist,
        closeCurrentPlaylist = playlistViewModel::closeCurrentPlaylist,
        deleteCurrentPlaylist = playlistViewModel::deleteCurrentPlaylist,
        onPlaylistSelected = playlistViewModel::onPlaylistSelected,
        removeSongsCurrentPlaylist = playlistViewModel::removeSongsCurrentPlaylist,
        removeDuplicatesInPlaylist = playlistViewModel::removeDuplicatesInPlaylist,
        renameCurrentPlaylist = playlistViewModel::renameCurrentPlaylist,
        sendActivePlaylistSong = playlistViewModel::sendActivePlaylistSong,
        sendAllPlaylists = playlistViewModel::sendAllPlaylists,
        sendCurrentPlaylist = playlistViewModel::sendCurrentPlaylist,
        sendPlaylistFavourite = playlistViewModel::sendPlaylistFavourite,
        setCurrentPlaylist = playlistViewModel::setCurrentPlaylist,
        shuffleAllPlaylists = playlistViewModel::shuffleAllPlaylists,
        shuffleCurrentPlaylist = playlistViewModel::shuffleCurrentPlaylist

    )

    PlaylistMediumScreen(
        callbacks,
        playlistsData
    )
}

/*
onConfirm = { action, dialog, expand ->
    pendingAction = action
    expanded = expand
    if (dialog) {
        showDialog = true
    } else {
        action?.invoke()
    }
}

 */