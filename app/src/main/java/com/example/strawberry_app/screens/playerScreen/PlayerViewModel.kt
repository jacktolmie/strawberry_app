package com.example.strawberry_app.screens.playerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.music.PlaylistRepository
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.EventType
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

enum class PlayState{
    PLAYING, PAUSED, STOPPED
}

data class PlayerValues(
    val activePlaylist: Int = -1,
    val currentSong: SongInfo? = null,
    val currentSongId: Long? = -1,
    val currentTime: Long = 0L,
    val playState: PlayState = PlayState.STOPPED,
    val songLength: Long = 0L,
    val volume: Int = 0
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val playlistRepository: PlaylistRepository
): ViewModel(){

//    private val _networkStatus = networkManager.connectionStateFlow

    private val _playerState = MutableStateFlow(PlayerValues())
    val playerState = _playerState.asStateFlow()

    val currentSong: StateFlow<SongInfo?> = _playerState
        .map { it.currentSong }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            networkManager.connectionStateFlow.collect { state ->
                if (state is ConnectionState.Disconnected || state is ConnectionState.Connecting){
                    _playerState.update { PlayerValues() }
                }
                if (state is ConnectionState.Connected) {
                    observeNetworkMessages()
                }
            }
        }

        viewModelScope.launch {
            playlistRepository.currentSongData.collect { songData ->
                _playerState.update { it.copy(songLength = songData?.length ?: 0L ) }
            }
        }

        // Start timing slider increment.
        timerUpdate()
    }

    private fun observeNetworkMessages() {
        viewModelScope.launch {
            networkManager.serverMessages.collect{ message ->
                when(message) {
                    is EventType.GuiUpdates -> {
                        _playerState.update {
                            PlayerValues(
                                activePlaylist =    message.active_playlist,
                                currentSongId =       message.current_song,
                                currentTime =       message.time,
                                playState =         when(message.playing){
                                                        "paused" ->     PlayState.PAUSED
                                                         "playing" ->    PlayState.PLAYING
                                                        else ->         PlayState.STOPPED
                                },
                                songLength =        _playerState.value.songLength,
                                volume =            message.volume
                            )
                        }
                    }

                    is EventType.VolumeChanged -> _playerState.update {it.copy(volume = message.volume) }
                    is EventType.Time -> _playerState.update { it.copy(currentTime = message.time) }
                    is EventType.SongChanged -> _playerState.update { it.copy(currentSongId = message.track_id) }
                    // If playing, update current GUI settings to match server.
                    is EventType.Play -> _playerState.update {
                        it.copy(
                            activePlaylist = message.active_playlist,
                            currentSongId = message.row,
                            currentTime = message.time,
                            playState = PlayState.PLAYING,
                            songLength = message.length
                        )
                    }
                    // If paused, sync time sent from server
                    is EventType.Pause -> _playerState.update {
                        it.copy(
                            currentTime = message.time,
                            playState = PlayState.PAUSED
                        )
                    }

                    // If stopped, set player screen to defaults.
                    is EventType.Stop -> _playerState.update {
                        val currentVolume = _playerState.value.volume
                        it.copy(
                            currentSong = SongInfo(),
                            currentTime = 0,
                            songLength = 0,
                            volume = currentVolume,
                            playState = PlayState.STOPPED
                        )
                    }

                    is EventType.SeekTo -> _playerState.update {it.copy(currentTime = message.time) }

                    // When song is changed, update the player screen.
                    is EventType.SongInfo -> _playerState.update {
                        it.copy(currentSong = SongInfo(
                            id = message.id,
                            artist = message.artist,
                            album = message.album,
                            title = message.title,
                            length = message.length
                        ))
                    }

                    else -> Unit
                }
            }
        }
    }

//    fun isConnected() = _networkStatus.value == ConnectionState.Connected
    fun sendMute() = sendCommand(OutgoingMessage.Mute)
    fun sendNext() = sendCommand(OutgoingMessage.Next)
//    fun sendPause(){
//        sendCommand(OutgoingMessage.Pause)
//        _playerState.update { it.copy(playState = PlayState.PAUSED) }
//    }
    fun sendPlayPause() {
        sendCommand(OutgoingMessage.PlayPause)
        _playerState.update { it.copy(
            playState = if (_playerState.value.playState == PlayState.PLAYING)
                PlayState.PAUSED else PlayState.PLAYING
        ) }
    }
//    fun sendPlay(){
//        sendCommand(OutgoingMessage.Play)
//        _playerState.update { it.copy(playState = PlayState.PLAYING) }
//    }
    fun sendPrevious() = sendCommand(OutgoingMessage.Previous)
    fun sendSeekBackward() = sendCommand(OutgoingMessage.SeekBackward)
    fun sendSeekForward() = sendCommand(OutgoingMessage.SeekForward)
    fun sendSeekTo(seekTo: Long) = sendCommand(OutgoingMessage.SeekTo(seekTo / 1000))
    fun sendStop() {
        sendCommand(OutgoingMessage.Stop)
        _playerState.update { it.copy(playState = PlayState.STOPPED) }
    }
    fun sendStopAfterCurrent() = sendCommand(OutgoingMessage.StopAfterCurrent)
    fun sendVolume(volume: Int) = sendCommand(OutgoingMessage.Volume(volume))
    fun sendVolumeDown() = sendCommand(OutgoingMessage.VolumeDown)
    fun sendVolumeUp() = sendCommand(OutgoingMessage.VolumeUp)

    fun sendCommand(command: OutgoingMessage) {
        viewModelScope.launch {
            networkManager.sendCommand(command)
        }
    }

    fun timeChanged(time: Long){
        _playerState.update { it.copy(currentTime = time) }
    }

    fun formatTime(time: Long): String {
        val hours = time / 3_600_000
        val minutes = (time % 3_600_000) / 60_000
        val seconds = (time % 60_000) / 1_000

        return  if (hours > 0L) "%d:%02d:%02d".format(hours, minutes, seconds)
                else "%d:%02d".format(minutes, seconds)
    }

    fun timerUpdate() {
        viewModelScope.launch {
            while(true) {
                delay(1000.milliseconds)
                if(_playerState.value.playState == PlayState.PLAYING){
                    _playerState.update { it.copy(currentTime = it.currentTime + 1000L) }
                }
            }
        }

    }
}