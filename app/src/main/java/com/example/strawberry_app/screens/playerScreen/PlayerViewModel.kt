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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PlayState{
    PLAYING,
    PAUSED,
    STOPPED
}

data class PlayerValues(
    val activePlaylist: Int = -1,
    val currentSong: Int = -1,
    val currentTime: Long = -1L,
    val playState: PlayState = PlayState.STOPPED,
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

    val playlistState = playlistRepository.playlistState

    init {
        observeNetworkMessages()
    }

    private fun observeNetworkMessages() {
        viewModelScope.launch {
            networkManager.serverMessages.collect{ message ->
                when(message) {
                    is EventType.GuiUpdates -> {
                        _playerState.update {
                            PlayerValues(
                                activePlaylist = message.active_playlist,
                                currentSong = message.current_song,
                                currentTime = message.current_time,
                                playState = when(message.playing){
                                    "paused" -> PlayState.PAUSED
                                    "playing" -> PlayState.PLAYING
                                    else -> PlayState.STOPPED
                                },
                                volume = message.volume
                            )
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    fun isConnected() = _networkStatus.value == ConnectionState.Connected
    fun sendMute() = sendCommand(OutgoingMessage.Mute)
    fun sendNext() = sendCommand(OutgoingMessage.Next)
    fun sendPause() = sendCommand(OutgoingMessage.Pause)
    fun sendPlayPause() = sendCommand(OutgoingMessage.PlayPause)
    fun sendPlay() = sendCommand(OutgoingMessage.Play)
    fun sendPrevious() = sendCommand(OutgoingMessage.Previous)
    fun sendSeekBackward() = sendCommand(OutgoingMessage.SeekBackward)
    fun sendSeekForward() = sendCommand(OutgoingMessage.SeekForward)
    fun sendSeekTo(seekTo: Int) = sendCommand(OutgoingMessage.SeekTo(seekTo))
    fun sendStop() = sendCommand(OutgoingMessage.Stop)
    fun sendStopAfterCurrent() = sendCommand(OutgoingMessage.StopAfterCurrent)
    fun setVolume(volume: Int) = sendCommand(OutgoingMessage.Volume(volume))
    fun sendVolumeDown() = sendCommand(OutgoingMessage.VolumeDown)
    fun sendVolumeUp() = sendCommand(OutgoingMessage.VolumeUp)

    fun sendCommand(command: OutgoingMessage) {
        viewModelScope.launch {
            networkManager.sendCommand(command)
        }
    }
}