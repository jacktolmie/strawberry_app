package com.example.strawberry_app.screens.playlistScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.io.File
import javax.inject.Inject

data class PlaylistsData(
    val playlistState: PlaylistState = PlaylistState(),
    val playlists: List<PlaylistEntity> = emptyList(),
    val playlistSongs: List<SongWithPosition> = emptyList()
)

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
): ViewModel(){

    val albumArtFile: StateFlow<File?> = playlistRepository.latestCover
        .map { name -> playlistRepository.getAlbumArtFile(name) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val playlistState = playlistRepository.playlistState

    val playlistsInfo : StateFlow<List<PlaylistEntity>> = playlistRepository.getPlaylists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedPlaylistId = MutableStateFlow(playlistRepository.playlistState.value.activePlaylist)

    @OptIn(ExperimentalCoroutinesApi::class)
    val playlistSongs: StateFlow<List<SongWithPosition>> = _selectedPlaylistId
        .flatMapLatest { id ->
            playlistRepository.getPlaylistSongs(id)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val playlistsData: StateFlow<PlaylistsData> = combine(
        playlistState,
        playlistsInfo,
        playlistSongs
    ) { state, playlists, songs ->
        PlaylistsData(state, playlists, songs)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlaylistsData()
    )

    fun onPlaylistSelected(id: Long) {
        _selectedPlaylistId.value = id
    }

    fun getAlbumArtFile(name: String): File? {
        return playlistRepository.getAlbumArtFile(name)
    }

    fun isCurrentPlaying(id: Long): Boolean {
        println("currentsongid ${playlistState.value.currentSongWithPosition?.id}")
        return id == playlistState.value.currentSongWithPosition?.id
    }


    // Functions to send playlist changes to the server.
    fun clearCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.ClearPlaylist(id))
    fun closeCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.CloseCurrent(id))
    fun deleteCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.DeleteCurrentPlaylist(id))
    fun removeSongsCurrentPlaylist(id: Long, songsList: List<Long>){
        playlistRepository.sendCommand(OutgoingMessage.RemoveCurrentSongsFromPlaylist(id = id, songsList = songsList))
    }
    fun removeDuplicatesInPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.RemoveDuplicatesFromPlaylist(id = id))
    fun renameCurrentPlaylist(id: Long, name: String) {
        playlistRepository.sendCommand(OutgoingMessage.RenamePlaylist(id = id, name = name))
    }
    fun sendActivePlaylistSong(id: Long, songIndex: Long){
        playlistRepository.sendCommand(OutgoingMessage.SendActivePlaylistSong(id = id, songIndex = songIndex))
    }
    fun sendAllPlaylists(playlists: List<Playlist>) = playlistRepository.sendCommand(OutgoingMessage.SendAllPlaylists(playlists = playlists))
    fun sendCurrentPlaylist(id: Long, playlist: Playlist){
        playlistRepository.sendCommand(OutgoingMessage.SendCurrentPlaylist(id = id))
    }
    fun sendPlaylistFavourite(id: Long, favourite: Boolean){
        playlistRepository.sendCommand(OutgoingMessage.FavouritePlaylist(id = id, favourite = favourite))
    }
    fun setCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.SetCurrentPlaylist(id = id))
    fun shuffleAllPlaylists() = playlistRepository.sendCommand(OutgoingMessage.ShuffleAllPlaylists)
    fun shuffleCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.ShuffleCurrentPlaylist(id = id))
}