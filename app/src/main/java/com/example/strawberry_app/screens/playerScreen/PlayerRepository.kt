package com.example.strawberry_app.screens.playerScreen

import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.protocol.OutgoingMessage
import com.example.strawberry_app.screens.ServerGuiValues
import com.example.strawberry_app.screens.playlistScreen.PlaylistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val albumArtRepository: AlbumArtRepository,
    private val playlistRepository: PlaylistRepository,
    @ApplicationScope private val scope: CoroutineScope
) {
    private val _serverUpdates = MutableStateFlow(ServerGuiValues())
    val serverUpdates = _serverUpdates.asStateFlow()

    private val _latestCover = MutableStateFlow("")
    val latestCover = _latestCover.asStateFlow()

    init {
        scope.launch {
            // Get updates from the server with the latest song information.
            playlistRepository.currentSongData.collectLatest { songWithPosition ->
                songWithPosition?.let{
                    _serverUpdates.update { state ->
                        state.copy(
                            currentSong = SongInfo(
                                id = it.id,
                                artist = it.artist,
                                album = it.album,
                                title = it.title,
                                length = it.length
                            )
                        )
                    }
                    checkAlbumArt(it)
                }
            }
        }
    }

    fun checkAlbumArt(songInfo: SongWithPosition){
        if (songInfo.coverImage.isEmpty()){
            notifyAlbumArtReady("")
            return
        }
        if (!albumArtRepository.hasImage(songInfo.coverImage)) {
            playlistRepository.sendCommand(OutgoingMessage.RequestCover)
        } else {
                notifyAlbumArtReady(songInfo.coverImage)
        }
    }
    fun getGuiUpdates(serverGui: ServerGuiValues){
        _serverUpdates.value = serverGui
    }

    fun notifyAlbumArtReady(name: String){
        _latestCover.value = name
    }

    fun sendCommand(command: OutgoingMessage) {
        playlistRepository.sendCommand(command)
    }

    fun getAlbumArtFile(name: String): File? {
        return if (albumArtRepository.hasImage(name)) {
            albumArtRepository.getImageFile(name)
        } else {
            null
        }
    }
}