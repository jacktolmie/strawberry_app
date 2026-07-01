package com.example.strawberry_app.screens.playlistScreen

import androidx.lifecycle.ViewModel
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
): ViewModel(){

    private val _playlistState = MutableStateFlow( PlaylistState())// PlaylistValues())
    val playlistState = _playlistState.asStateFlow()

    // Functions to send playlist changes to the server.
    fun clearCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.ClearPlaylist(id))
    fun closeCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.CloseCurrent(id))
    fun deleteCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.DeleteCurrentPlaylist(id))
    fun removeSongsCurrentPlaylist(id: Long, songsList: List<Long>){
        playlistRepository.sendCommand(OutgoingMessage.RemoveCurrentSongsFromPlaylist(id = id, songsList = songsList))
    }
    fun removeDuplicatesInPlaylist() = playlistRepository.sendCommand(OutgoingMessage.RemoveDuplicatesFromPlaylist)
    fun renameCurrentPlaylist(id: Long, name: String) {
        playlistRepository.sendCommand(OutgoingMessage.RenamePlaylist(id = id, name = name))
    }
    fun sendActivePlaylistSong(id: Long, songIndex: Long){
        playlistRepository.sendCommand(OutgoingMessage.SendActivePlaylistSong(id = id, songIndex = songIndex))
    }
    fun sendAllPlaylists(playlists: List<Playlist>) = playlistRepository.sendCommand(OutgoingMessage.SendAllPlaylists(playlists = playlists))
    fun sendCurrentPlaylist(id: Long, playlist: Playlist){
        playlistRepository.sendCommand(OutgoingMessage.SendCurrentPlaylist(id = id, playlist =  playlist))
    }
    fun sendPlaylistFavourite(id: Long, favourite: Boolean){
        playlistRepository.sendCommand(OutgoingMessage.FavouritePlaylist(playlistId = id, favourite = favourite))
    }
    fun setCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.SetCurrentPlaylist(id = id))
    fun shuffleAllPlaylists() = playlistRepository.sendCommand(OutgoingMessage.ShuffleAllPlaylists)
    fun shuffleCurrentPlaylist(id: Long) = playlistRepository.sendCommand(OutgoingMessage.ShuffleCurrentPlaylist(id = id))
}