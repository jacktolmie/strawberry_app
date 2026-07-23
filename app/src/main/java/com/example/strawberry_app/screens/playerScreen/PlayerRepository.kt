package com.example.strawberry_app.screens.playerScreen

import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.protocol.OutgoingMessage
import com.example.strawberry_app.screens.AlbumArtRepository
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
    @param:ApplicationScope private val scope: CoroutineScope
) {
    private val _serverUpdates = MutableStateFlow(ServerGuiValues())
    val serverUpdates = _serverUpdates.asStateFlow()

    val latestCover = albumArtRepository.latestCover

    init {
        scope.launch {
            // Get updates from the server with the latest song information.
            playlistRepository.currentSongData.collectLatest { songWithPosition ->

                songWithPosition?.let {
                    _serverUpdates.update { state ->
                        state.copy(
                            currentSong = SongInfo(
                                artist = it.artist,
                                album = it.album,
                                coverImage = it.coverImage,
                                id = it.id,
                                length = it.length,
                                playlistId = it.playlistId,
                                title = it.title,
                                url = it.url
                            )
                        )
                    }
                    albumArtRepository.checkAlbumArt( name = it.coverImage )
                }
            }
        }
    }

    fun getGuiUpdates(serverGui: ServerGuiValues) {
        _serverUpdates.value = serverGui
    }

    fun sendCommand(command: OutgoingMessage) {
        playlistRepository.sendCommand(command)
    }

    fun getAlbumArtFile(name: String): File? {
        return playlistRepository.getAlbumArtFile(name)
    }
}