package com.example.strawberry_app.screens.playlistScreen

import androidx.lifecycle.ViewModel
import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.music.PlaylistRepository
import com.example.strawberry_app.network.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class PlaylistValues(
    val activePlaylist: Long = -1L,
    val currentPlaylist: Long = -1L,
    val currentSongId: Long = -1L
)

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val playlistRepository: PlaylistRepository
): ViewModel(){

    private val _playlistState = MutableStateFlow(PlaylistValues())
    val playlistState = _playlistState.asStateFlow()

    init {
//        playlistRepository.
    }
}