package com.example.strawberry_app.screens.playlistScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    val albumArtCollection = playlistRepository.artAlbumCollection

    var dragIconSongId by mutableStateOf<Long?>(null)

    val playlistState = playlistRepository.playlistState

    val playlistsInfo : StateFlow<List<PlaylistEntity>> = playlistRepository.getPlaylists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedPlaylistId = playlistRepository.playlistState
        .map { it.currentPlaylist }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = -1L
        )

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

    // Set drag icon for currently selected song index.
    fun setDragIcon(songIndex: Long?){ dragIconSongId = songIndex }
    fun isDragIconSong(songIndex: Long) = songIndex == dragIconSongId

    fun onPlaylistSelected(id: Long) =playlistRepository.setCurrentPlaylist(id)

    fun getAlbumArtFile(coverArt: String): File? = playlistRepository.getAlbumArtFile(coverArt)

    fun isCurrentPlaying(id: Long) = id == playlistState.value.currentSongWithPosition?.id

    // Functions etc. for selecting songs in playlist to delete
    private val _selectedSongs = mutableStateOf<List<Long>>(emptyList())
    val selectedSongs: State<List<Long>> = _selectedSongs

    val isInSelectedMode: StateFlow<Boolean> = snapshotFlow {
        _selectedSongs.value.isNotEmpty()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun clearSelectedSongs() {
        _selectedSongs.value = emptyList()
    }

    fun deleteSelectedSongs(id: Long){
        removeSongsCurrentPlaylist(id = id,  _selectedSongs.value)
        _selectedSongs.value = emptyList()
    }
    fun toggleSelection(songIndex: Long){
        println("playlistvm toggleselection called with song index $songIndex")
        _selectedSongs.value = if(_selectedSongs.value.contains(songIndex))
             _selectedSongs.value.minus(songIndex)
        else _selectedSongs.value.plus(songIndex)
        println("playlistvm songs in list:${_selectedSongs.value}")
    }

    // Functions to send playlist changes to the server.
    fun clearCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.ClearPlaylist(id))
    fun closeCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.CloseCurrent(id))
    fun deleteCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.DeleteCurrentPlaylist(id))
    fun removeSongsCurrentPlaylist(id: Long, songsList: List<Long>){
        playlistRepository.sendCommand(OutgoingMessage.RemoveCurrentSongsFromPlaylist(id = id, songsList = songsList))
        _selectedSongs.value = emptyList()
    }
    fun removeDuplicatesInPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.RemoveDuplicatesFromPlaylist(id = id))
    fun removeUnavailableSongs(id: Long) = playlistRepository.sendCommand(OutgoingMessage.RemoveUnavailableSongs(id = id))
    fun renameCurrentPlaylist(id: Long, name: String) {
        playlistRepository.sendCommand(OutgoingMessage.RenamePlaylist(id = id, name = name))
    }
    fun sendActivePlaylistSong(id: Long, songIndex: Long){
        playlistRepository.sendCommand(OutgoingMessage.SendActivePlaylistSong(id = id, songIndex = songIndex))
    }
    fun sendAllPlaylists(playlists: List<Playlist>) = playlistRepository.sendCommand(OutgoingMessage.SendAllPlaylists(playlists = playlists))
    fun sendCurrentChangedPlaylist(id: Long, toIndex: Long, fromIndex: Long){
        playlistRepository.sendCommand(
            OutgoingMessage.SendCurrentChangedPlaylist(
                id = id,
                toIndex = toIndex,
                fromIndex = fromIndex
            )
        )
    }
    fun sendPlaylistFavourite(id: Long, favourite: Boolean){
        playlistRepository.sendCommand(OutgoingMessage.FavouritePlaylist(id = id, favourite = favourite))
    }
    fun setCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.SetCurrentPlaylist(id = id))
    fun sendRepeatMode(repeatMode: String) = playlistRepository.sendCommand(OutgoingMessage.RepeatMode(repeatMode))
    fun shuffleAllPlaylists() = playlistRepository.sendCommand(OutgoingMessage.ShuffleAllPlaylists)
    fun shuffleCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.ShuffleCurrentPlaylist(id = id))
}