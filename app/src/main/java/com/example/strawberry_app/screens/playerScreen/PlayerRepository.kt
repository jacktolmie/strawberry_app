package com.example.strawberry_app.screens.playerScreen

import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.protocol.OutgoingMessage
import com.example.strawberry_app.screens.ServerGuiValues
import com.example.strawberry_app.screens.playlistScreen.PlaylistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
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

    private val _latestCover = MutableStateFlow("")
    val latestCover = _latestCover.asStateFlow()

    private val _albumArtReady = MutableSharedFlow<String>(replay = 1)
    val albumArtReady = _albumArtReady.asSharedFlow()

    fun getGuiUpdates(serverGui: ServerGuiValues){
        _serverUpdates.value = serverGui
    }

    fun notifyAlbumArtReady(name: String){
        _latestCover.value = name
    }

    fun sendCommand(command: OutgoingMessage) {
        playlistRepository.sendCommand(command)
    }

    // Check for album cover. Make request if missing.
    fun checkAlbumArt(){
        val imageName = playlistRepository.playlistState.value.currentSongData?.coverImage ?: ""
        if(imageName.isNotEmpty() && !albumArtRepository.hasImage(imageName)){
            playlistRepository.sendCommand(OutgoingMessage.RequestCover)
        }
    }

    fun getAlbumArtFile(name: String): File? {
        return if (albumArtRepository.hasImage(name)) {
            albumArtRepository.getImageFile(name)
        } else {
            null
        }
    }
}