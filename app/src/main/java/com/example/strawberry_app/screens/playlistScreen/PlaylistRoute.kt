package com.example.strawberry_app.screens.playlistScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.screens.navigation.ScreenType
import com.example.strawberry_app.screens.playlistScreen.screens.PlaylistPortraitScreen
import java.io.File

data class PlaylistCallbacks(
    val clearCurrentPlaylist: (id: Long) -> Unit = {},
    val clearSelectedSongs: () -> Unit = {},
    val closeCurrentPlaylist: (id: Long) -> Unit = {},
    val deleteCurrentPlaylist: (id: Long) -> Unit = {},
    val deleteSelectedSongs: (id: Long) -> Unit = {},
    val getAlbumArtFile: (coverArt: String) -> File? = {null},
    val isCurrentPlaying: (id: Long) -> Boolean = { false },
    val isDragIconSong: (songIndex: Long) -> Boolean = { false },
    val onPlaylistSelected: (id: Long) -> Unit = {},
    val remoteSentActive: (id: Long, songIndex: Long) -> Unit = {_,_->},
    val removeDuplicatesInPlaylist: (id: Long) -> Unit = {},
    val removeUnavailableSongs: (id: Long) -> Unit = {},
    val renameCurrentPlaylist: (id: Long, name: String) -> Unit = { _,_ ->},
    val sendActivePlaylistSong: () -> Unit = {},
    val sendAllPlaylists: (playlists: List<Playlist>) -> Unit = {},
    val sendCurrentChangedPlaylist: (id: Long, toIndex: Long, fromIndex: Long) -> Unit = { _,_,_ ->},
    val sendPlaylistFavourite: (id: Long, favourite: Boolean) -> Unit = { _,_ ->},
    val sendRepeatMode: (mode: String) -> Unit = {},
    val sendShuffleMode: (mode: String) -> Unit = {},
    val setCurrentPlaylist: (id: Long) -> Unit = {},
    val setDragIcon: (songIndex: Long?) -> Unit = {},
    val toggleSelection: (id: Long) -> Unit = {},
    val updateCurrentSong: (id: Long, songIndex: Long) -> Unit = {_,_ ->}
    )

@Composable
fun PlaylistRoute(
    isPortrait: Boolean,
    screenType: ScreenType,
    playlistViewModel: PlaylistViewModel = hiltViewModel()
){
    val selectedSongs by playlistViewModel.selectedSongs
    val dragIconSongId = playlistViewModel.dragIconSongId
    val isInSelectedMode by playlistViewModel.isInSelectedMode.collectAsStateWithLifecycle()
    val playlistsData by playlistViewModel.playlistsData.collectAsStateWithLifecycle()
    val albumArtCollection by playlistViewModel.albumArtCollection.collectAsStateWithLifecycle()

    val callbacks = PlaylistCallbacks(
        clearCurrentPlaylist = playlistViewModel::clearCurrentPlaylist,
        clearSelectedSongs = playlistViewModel::clearSelectedSongs,
        closeCurrentPlaylist = playlistViewModel::closeCurrentPlaylist,
        deleteCurrentPlaylist = playlistViewModel::deleteCurrentPlaylist,
        deleteSelectedSongs = playlistViewModel::deleteSelectedSongs,
        getAlbumArtFile = playlistViewModel::getAlbumArtFile,
        isCurrentPlaying = playlistViewModel::isCurrentSongPlaying,
        isDragIconSong = playlistViewModel::isDragIconSong,
        onPlaylistSelected = playlistViewModel::onPlaylistSelected,
        remoteSentActive = playlistViewModel::remoteSentActive,
        removeDuplicatesInPlaylist = playlistViewModel::removeDuplicatesInPlaylist,
        removeUnavailableSongs = playlistViewModel::removeUnavailableSongs,
        renameCurrentPlaylist = playlistViewModel::renameCurrentPlaylist,
        sendActivePlaylistSong = playlistViewModel::sendActivePlaylistSong,
        sendAllPlaylists = playlistViewModel::sendAllPlaylists,
        sendCurrentChangedPlaylist = playlistViewModel::sendCurrentChangedPlaylist,
        sendPlaylistFavourite = playlistViewModel::sendPlaylistFavourite,
        sendRepeatMode = playlistViewModel::sendRepeatMode,
        setCurrentPlaylist = playlistViewModel::setCurrentPlaylist,
        setDragIcon = playlistViewModel::setDragIcon,
        sendShuffleMode = playlistViewModel::sendShuffleMode,
        toggleSelection = playlistViewModel::toggleSelection,
        updateCurrentSong = playlistViewModel::updateCurrentSong
    )

    val playlistScreenState = PlaylistScreenState(
        albumArtFile = albumArtCollection,
        dragIconSongId = dragIconSongId,
        isInSelectedMode = isInSelectedMode,
        playlistsData = playlistsData,
        selectedSongs = selectedSongs
    )

    when (screenType) {
        ScreenType.SMALL_PHONE,
        ScreenType.MEDIUM_PHONE,
        ScreenType.LARGE_PHONE -> {
            PlaylistPortraitScreen(
                callbacks = callbacks,
                isPortrait = isPortrait,
                playlistScreenState = playlistScreenState,
                screenType = screenType
            )
        }
        ScreenType.FOLDABLE -> {
            println("myapp foldable called")
        }
        ScreenType.TABLET -> {
            println("myapp tablet called")
        }
    }
}