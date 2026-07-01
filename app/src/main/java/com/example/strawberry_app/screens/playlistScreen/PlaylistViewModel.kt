package com.example.strawberry_app.screens.playlistScreen

import androidx.lifecycle.ViewModel
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.screens.playerScreen.PlaylistValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistRepository: PlaylistRepository
): ViewModel(){

    private val _playlistState = MutableStateFlow(PlaylistValues())
    val playlistState = _playlistState.asStateFlow()

    // Functions to send playlist changes to the server.
    fun clearCurrentPlaylist(id: Long) = playlistRepository.clearCurrentPlaylist(id)
    fun closeCurrentPlaylist(id: Long) = playlistRepository.closeCurrentPlaylist(id)
    fun deleteCurrentPlaylist(id: Long) = playlistRepository.deleteCurrentPlaylist(id)
    fun removeSongsCurrentPlaylist(id: Long, songsList: List<Long>){
        playlistRepository.removeSongsCurrentPlaylist(id = id, songsList = songsList)
    }
    fun removeDuplicatesInPlaylist() = playlistRepository.removeDuplicatesInPlaylist()
    fun renameCurrentPlaylist(id: Long, name: String) {
        playlistRepository.renameCurrentPlaylist(id = id, name = name)
    }
    fun sendActivePlaylistSong(id: Long, song: Long){
        playlistRepository.sendActivePlaylistSong(id = id, songIndex = song)
    }

    fun sendAllPlaylists(playlists: List<Playlist>) = playlistRepository.sendAllPlaylists(playlists = playlists)
    fun sendCurrentPlaylist(id: Long, playlist: Playlist){
        playlistRepository.sendCurrentPlaylist(id = id, playlist = playlist)
    }
    fun sendPlaylistFavourite(id: Long, favourite: Boolean){
        playlistRepository.sendPlaylistFavourite(id = id, favourite = favourite)
    }
    fun setCurrentPlaylist(id: Long) = playlistRepository.setCurrentPlaylist(id = id)
    fun shuffleAllPlaylists() = playlistRepository.shuffleAllPlaylists()
    fun shuffleCurrentPlaylist(id: Long) = playlistRepository.shuffleCurrentPlaylist(id = id)

    suspend fun serverRenamedPlaylist(id: Long, name: String){
        playlistRepository.serverRenamedPlaylist(id = id, name = name)
    }

}