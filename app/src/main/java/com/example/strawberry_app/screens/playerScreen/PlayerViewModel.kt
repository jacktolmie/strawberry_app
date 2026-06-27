package com.example.strawberry_app.screens.playerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.OutgoingMessage
import com.example.strawberry_app.screens.MessageRepository
import com.example.strawberry_app.screens.PlayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val messageRepository: MessageRepository
): ViewModel(){

    val serverUpdates = messageRepository.serverUpdates

    init {
        // Start timing slider increment.
        timerUpdate()
    }

    fun sendMute() = sendCommand(OutgoingMessage.Mute)
    fun sendNext() = sendCommand(OutgoingMessage.Next)
    fun sendRestartPrevious() = sendCommand(OutgoingMessage.RestartOrPrevious)
    fun sendPlayPause() {
        sendCommand(OutgoingMessage.PlayPause)
        messageRepository.updateInformation(messageRepository.serverUpdates.value.copy(
            playState = if (messageRepository.serverUpdates.value.playState == PlayState.PLAYING)
                PlayState.PAUSED else PlayState.PLAYING
        ))
    }

    fun sendPrevious() = sendCommand(OutgoingMessage.Previous)
    fun sendSeekBackward() = sendCommand(OutgoingMessage.SeekBackward)
    fun sendSeekForward() = sendCommand(OutgoingMessage.SeekForward)
    fun sendSeekTo(seekTo: Long) = sendCommand(OutgoingMessage.SeekTo(seekTo / 1000))

    fun sendStop() {
        sendCommand(OutgoingMessage.Stop)
        messageRepository.updateInformation(messageRepository.serverUpdates.value.copy(playState = PlayState.STOPPED))
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
        messageRepository.updateInformation(messageRepository.serverUpdates.value.copy(currentTime = time))
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
                if(messageRepository.serverUpdates.value.playState == PlayState.PLAYING){
                    messageRepository.updateInformation(
                    messageRepository.serverUpdates.value.copy(
                        currentTime = messageRepository.serverUpdates.value.currentTime + 1000L)
                    )
                }
            }
        }
    }
}