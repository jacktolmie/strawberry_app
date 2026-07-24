package com.example.strawberry_app.screens.playlistScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.screens.playlistScreen.screens.PlaylistMediumScreen
import java.io.File

data class PlaylistScreenState(
    val playlistsData: PlaylistsData = PlaylistsData(),
    val albumArtFile: Map<String, File?> = emptyMap()
)

data class PlaylistCallbacks(
    val clearCurrentPlaylist: (id: Long) -> Unit = {},
    val closeCurrentPlaylist: (id: Long) -> Unit = {},
    val deleteCurrentPlaylist: (id: Long) -> Unit = {},
    val getAlbumArtFile: (coverArt: String) -> File? = {null},
    val isCurrentPlaying: (id: Long) -> Boolean = { false },
    val onPlaylistSelected: (id: Long) -> Unit = {},
    val removeSongsCurrentPlaylist: (id: Long, songsList: List<Long>) -> Unit = { _,_ ->},
    val removeDuplicatesInPlaylist: (id: Long) -> Unit = {},
    val renameCurrentPlaylist: (id: Long, name: String) -> Unit = { _,_ ->},
    val sendActivePlaylistSong: (id: Long, songIndex: Long) -> Unit = { _,_ ->},
    val sendAllPlaylists: (playlists: List<Playlist>) -> Unit = {},
    val sendCurrentPlaylist: (id: Long, playlist: Playlist) -> Unit = { _,_ ->},
    val sendPlaylistFavourite: (id: Long, favourite: Boolean) -> Unit = { _,_ ->},
    val sendRepeatMode: (mode: String) -> Unit = {},
    val setCurrentPlaylist: (id: Long) -> Unit = {},
    val shuffleAllPlaylists: () -> Unit = {},
    val shuffleCurrentPlaylist: (id: Long) -> Unit = {}
    )

@Composable
fun PlaylistRoute(
    playlistViewModel: PlaylistViewModel = hiltViewModel()
){
    val playlistsData by playlistViewModel.playlistsData.collectAsStateWithLifecycle()
    val albumArtFile by playlistViewModel.albumArtCollection.collectAsStateWithLifecycle()

    val callbacks = PlaylistCallbacks(
        clearCurrentPlaylist = playlistViewModel::clearCurrentPlaylist,
        closeCurrentPlaylist = playlistViewModel::closeCurrentPlaylist,
        deleteCurrentPlaylist = playlistViewModel::deleteCurrentPlaylist,
        getAlbumArtFile = playlistViewModel::getAlbumArtFile,
        isCurrentPlaying = playlistViewModel::isCurrentPlaying,
        onPlaylistSelected = playlistViewModel::onPlaylistSelected,
        removeSongsCurrentPlaylist = playlistViewModel::removeSongsCurrentPlaylist,
        removeDuplicatesInPlaylist = playlistViewModel::removeDuplicatesInPlaylist,
        renameCurrentPlaylist = playlistViewModel::renameCurrentPlaylist,
        sendActivePlaylistSong = playlistViewModel::sendActivePlaylistSong,
        sendAllPlaylists = playlistViewModel::sendAllPlaylists,
        sendCurrentPlaylist = playlistViewModel::sendCurrentPlaylist,
        sendPlaylistFavourite = playlistViewModel::sendPlaylistFavourite,
        sendRepeatMode = playlistViewModel::sendRepeatMode,
        setCurrentPlaylist = playlistViewModel::setCurrentPlaylist,
        shuffleAllPlaylists = playlistViewModel::shuffleAllPlaylists,
        shuffleCurrentPlaylist = playlistViewModel::shuffleCurrentPlaylist

    )

    val playlistScreenState = PlaylistScreenState(
        playlistsData = playlistsData,
        albumArtFile = albumArtFile
    )

    PlaylistMediumScreen(
        callbacks = callbacks,
        playlistScreenState = playlistScreenState
    )
}