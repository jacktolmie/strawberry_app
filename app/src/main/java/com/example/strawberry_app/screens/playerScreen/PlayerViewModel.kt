package com.example.strawberry_app.screens.playerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.music.PlaylistRepository
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.EventType
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PlayState{
    PLAYING, PAUSED, STOPPED
}

data class PlayerValues(
    val activePlaylist: Int = -1,
    val currentSong: Int = -1,
    val currentTime: Long = -1L,
    val playState: PlayState = PlayState.STOPPED,
    val songLength: Long = -1L,
    val volume: Int = 0
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val playlistRepository: PlaylistRepository
): ViewModel(){

    private val _networkStatus = networkManager.connectionStateFlow

    private val _playerState = MutableStateFlow(PlayerValues())
    val playerState = _playerState.asStateFlow()

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
                _playerState.update { it.copy(songLength = songData?.length ?: 0L) }
            }
        }
    }


    private fun observeNetworkMessages() {
        viewModelScope.launch {
            networkManager.serverMessages.collect{ message ->
                when(message) {
                    is EventType.GuiUpdates -> {
                        _playerState.update {
                            PlayerValues(
                                activePlaylist =    message.active_playlist,
                                currentSong =       message.current_song,
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

                    is EventType.VolumeChanged -> _playerState.update { it.copy(volume = message.volume) }
                    is EventType.Time -> _playerState.update { it.copy(currentTime = message.time) }
                    is EventType.SongChanged -> _playerState.update { it.copy(currentSong = message.track_id) }
                    // Update play/pause button
                    is EventType.Play -> _playerState.update {
                        it.copy(playState = PlayState.PLAYING)
                    }
                    is EventType.Pause -> _playerState.update { it.copy(playState = PlayState.PAUSED) }
                    is EventType.Stop -> _playerState.update { it.copy(playState = PlayState.STOPPED) }

                    is EventType.SeekTo -> _playerState.update {it.copy(currentTime = message.time) }

                    else -> Unit
                }
            }
        }
    }

    fun isConnected() = _networkStatus.value == ConnectionState.Connected
    fun sendMute() = sendCommand(OutgoingMessage.Mute)
    fun sendNext() = sendCommand(OutgoingMessage.Next)
    fun sendPause(){
        sendCommand(OutgoingMessage.Pause)
        _playerState.update { it.copy(playState = PlayState.PAUSED) }
    }
    fun sendPlayPause() = sendCommand(OutgoingMessage.PlayPause)
    fun sendPlay(){
        sendCommand(OutgoingMessage.Play)
        _playerState.update { it.copy(playState = PlayState.PLAYING) }
    }
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
}