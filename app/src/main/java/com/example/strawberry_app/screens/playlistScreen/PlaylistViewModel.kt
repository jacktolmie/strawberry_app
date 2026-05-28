package com.example.strawberry_app.screens.playlistScreen

import androidx.lifecycle.ViewModel
import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.network.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistDao: PlaylistDao,
    private val playlistSongDao: PlaylistSongDao,
    private val networkManager: NetworkManager
): ViewModel() {

//    val
}