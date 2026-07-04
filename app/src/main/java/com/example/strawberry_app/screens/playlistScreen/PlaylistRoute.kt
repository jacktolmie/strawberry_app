package com.example.strawberry_app.screens.playlistScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.screens.playlistScreen.screens.PlaylistMediumScreen

data class PlaylistCallbacks(
    val clearCurrentPlaylist: (id: Long) -> Unit = {},
    val closeCurrentPlaylist: (id: Long) -> Unit = {},
    val deleteCurrentPlaylist: (id: Long) -> Unit = {},
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

data class PlaylistsData(
    val playlistState: PlaylistState = PlaylistState(),
    val playlists: List<PlaylistEntity> = emptyList()
)

@Composable
fun PlaylistRoute(
    playlistViewModel: PlaylistViewModel = hiltViewModel()
){
    val playlistState by playlistViewModel.playlistState.collectAsStateWithLifecycle()
    val playlists by playlistViewModel.playlistsInfo.collectAsStateWithLifecycle()

    val callbacks = PlaylistCallbacks(
        clearCurrentPlaylist = playlistViewModel::clearCurrentPlaylist,
        closeCurrentPlaylist = playlistViewModel::closeCurrentPlaylist,
        deleteCurrentPlaylist = playlistViewModel::deleteCurrentPlaylist,
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

    val playlistScreenData = PlaylistsData(
        playlistState = playlistState,
        playlists = playlists
        )

    PlaylistMediumScreen(callbacks, playlistScreenData)

}