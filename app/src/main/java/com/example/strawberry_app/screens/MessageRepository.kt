package com.example.strawberry_app.screens

import com.example.strawberry_app.music.PlaylistRepository
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.EventType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val networkManager: NetworkManager,
    private val playlistRepository: PlaylistRepository,
    @ApplicationScope private val scope: CoroutineScope
){
    private val _serverUpdates = MutableStateFlow(ServerValues())
    val serverUpdates = _serverUpdates.asStateFlow()
//    val playlistUpdates = MutableStateFlow<PlaylistRepository>

    private var observeJob: Job? = null

    init {
        scope.launch {
            networkManager.connectionStateFlow.collect { state ->
                if (state is ConnectionState.Disconnected || state is ConnectionState.Connecting){
                    resetServerValues()
                }
                if (state is ConnectionState.Connected){
                    observeNetworkMessages()
                }
            }
        }
    }

    private fun observeNetworkMessages() {
        observeJob?.cancel()
        scope.launch {
            networkManager.serverMessages.collect{ message ->
                when(message) {
                    is EventType.GuiUpdates -> {
                        when (message.playlists) {
                            is EventType.MakeAllPlaylists -> playlistRepository.makeAllPlaylists(message.playlists.playlists)
                        }
                        _serverUpdates.update {
                            ServerValues(
                                activePlaylist =    message.activePlaylist,
                                currentPlaylist =   message.currentPlaylist,
                                currentSongId =     message.currentSong,
                                currentTime =       message.time,
                                playState =         when(message.playing){
                                                        "paused" ->     PlayState.PAUSED
                                                         "playing" ->    PlayState.PLAYING
                                                        else ->         PlayState.STOPPED
                                },
                                volume =            message.volume,
                                currentSong =       playlistRepository.currentSongData.collect { it }
                            )
                        }
                    }
                    is EventType.MakeCurrentPlaylist -> playlistRepository.makeCurrentPlaylist(message.playlist)
                    // If playing, update current GUI settings to match server.
                    is EventType.Play -> _serverUpdates.update {
                        it.copy(
                            activePlaylist = message.active_playlist,
                            currentSongId = message.row,
                            currentTime = message.time,
                            playState = PlayState.PLAYING,
                            songLength = message.
                        )
                    }
                    // If paused, sync time sent from server
                    is EventType.Pause -> _serverUpdates.update {
                        it.copy(
                            currentTime = message.time,
                            playState = PlayState.PAUSED
                        )
                    }
                    is EventType.SeekTo -> _serverUpdates.update {it.copy(currentTime = message.time) }
                    is EventType.SongChanged -> _serverUpdates.update { it.copy(currentSongId = message.track_id) }
                    // When song is changed, update the player screen.
                    is EventType.SongInfo -> _serverUpdates.update {
                        it.copy(currentSong = SongInfo(
                            id = message.id,
                            artist = message.artist,
                            album = message.album,
                            title = message.title,
                            length = message.length
                        ))
                    }
                    // If stopped, set player screen to defaults.
                    is EventType.Stop -> _serverUpdates.update {
                        val currentVolume = _serverUpdates.value.volume
                        it.copy(
                            currentSong = SongInfo(),
                            currentTime = 0,
                            songLength = 0,
                            volume = currentVolume,
                            playState = PlayState.STOPPED
                        )
                    }
                    is EventType.Time -> _serverUpdates.update { it.copy(currentTime = message.time) }
                    is EventType.VolumeChanged -> _serverUpdates.update {it.copy(volume = message.volume) }
                    else -> Unit
                }
            }
        }
    }

    fun resetServerValues() {
        _serverUpdates.update { ServerValues() }
    }

    fun updateInformation(updates: ServerValues){
        _serverUpdates.value = updates
    }
}