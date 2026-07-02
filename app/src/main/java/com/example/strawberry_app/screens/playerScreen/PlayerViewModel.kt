package com.example.strawberry_app.screens.playerScreen

import androidx.compose.ui.res.pluralStringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.network.protocol.OutgoingMessage
import com.example.strawberry_app.screens.MessageRepository
import com.example.strawberry_app.screens.PlayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val messageRepository: MessageRepository
): ViewModel(){

    val serverUpdates = playerRepository.serverUpdates

    val albumArtFile: File? = currentSongData?.coverImage
        ?.takeIf { it.isNotEmpty() && albumArtRepository.hasImage(it) }
        ?.let { albumArtRepository.getImageFile(it) }

    init {
        // Start timing slider increment.
        timerUpdate()
    }

    fun sendMute() = playerRepository.sendCommand(OutgoingMessage.Mute)
    fun sendNext() = playerRepository.sendCommand(OutgoingMessage.Next)
    fun sendRestartPrevious() = playerRepository.sendCommand(OutgoingMessage.RestartOrPrevious)
    fun sendPlayPause() {
        playerRepository.sendCommand(OutgoingMessage.PlayPause)
        playerRepository.getGuiUpdates(playerRepository.serverUpdates.value.copy(
            playState = if (messageRepository.serverUpdates.value.playState == PlayState.PLAYING)
                PlayState.PAUSED else PlayState.PLAYING
        ))
    }

    fun sendPrevious() = playerRepository.sendCommand(OutgoingMessage.Previous)
    fun sendSeekBackward() = playerRepository.sendCommand(OutgoingMessage.SeekBackward)
    fun sendSeekForward() = playerRepository.sendCommand(OutgoingMessage.SeekForward)
    fun sendSeekTo(seekTo: Long) = playerRepository.sendCommand(OutgoingMessage.SeekTo(seekTo / 1000))

    fun sendStop() {
        playerRepository.sendCommand(OutgoingMessage.Stop)
        playerRepository.getGuiUpdates(playerRepository.serverUpdates.value.copy(playState = PlayState.STOPPED))
    }

    fun sendStopAfterCurrent() = playerRepository.sendCommand(OutgoingMessage.StopAfterCurrent)
    fun sendVolume(volume: Int) = playerRepository.sendCommand(OutgoingMessage.Volume(volume))
    fun sendVolumeDown() = playerRepository.sendCommand(OutgoingMessage.VolumeDown)
    fun sendVolumeUp() = playerRepository.sendCommand(OutgoingMessage.VolumeUp)

    fun timeChanged(time: Long){
        playerRepository.getGuiUpdates(playerRepository.serverUpdates.value.copy(currentTime = time))
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
                if(playerRepository.serverUpdates.value.playState == PlayState.PLAYING){
                    playerRepository.getGuiUpdates(
                        messageRepository.serverUpdates.value.copy(
                        currentTime = messageRepository.serverUpdates.value.currentTime + 1000L)
                    )
                }
            }
        }
    }
}