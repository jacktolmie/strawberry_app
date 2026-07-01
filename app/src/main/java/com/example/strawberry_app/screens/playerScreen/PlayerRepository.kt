package com.example.strawberry_app.screens.playerScreen

import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.protocol.OutgoingMessage
import com.example.strawberry_app.screens.ServerGuiValues
import com.example.strawberry_app.screens.playlistScreen.PlaylistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val albumArtRepository: AlbumArtRepository,
    private val playlistRepository: PlaylistRepository,
    @ApplicationScope
    private val scope: CoroutineScope
) {
    private val _serverUpdates = MutableStateFlow(ServerGuiValues())
    val serverUpdates = _serverUpdates.asStateFlow()

    fun getGuiUpdates(serverGui: ServerGuiValues){
        _serverUpdates.value = serverGui
    }

    fun sendCommand(command: OutgoingMessage) {
        playlistRepository.sendCommand(command)
    }

    // Check for album cover. Make request if missing.
    fun checkAlbumArt(){
        if(albumArtRepository.checkForCover(playlistRepository.playlistState.value.currentSongData.coverImage)){

        }
    }

    fun receiveCover(name: String){

    }
}