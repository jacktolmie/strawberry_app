package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("command")
sealed class OutgoingMessage {

    @Serializable
    @SerialName("clear-music")
    data class ClearPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("close-music")
    data class CloseCurrent(val id: Int) : OutgoingMessage()

    @Serializable
    @SerialName("delete-current-music")
    data class DeleteCurrentPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("favourite-music")
    data class FavouritePlaylist(val favourite: Boolean): OutgoingMessage()

    @Serializable
    @SerialName("mute")
    data object Mute: OutgoingMessage()

    @Serializable
    @SerialName("next")
    data object Next: OutgoingMessage()

    @Serializable
    @SerialName("pause")
    data object Pause: OutgoingMessage()

    @Serializable
    @SerialName("play-pause")
    data object PlayPause: OutgoingMessage()

    @Serializable
    @SerialName("play")
    data object Play: OutgoingMessage()

    @Serializable
    @SerialName("previous")
    data object Previous: OutgoingMessage()

    @Serializable
    @SerialName("remove-current-song-music")
    data class RemoveCurrentSongFromPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("remove-duplicates-music")
    data object RemoveDuplicatesFromPlaylist: OutgoingMessage()

    @Serializable
    @SerialName("rename-music")
    data class RenamePlaylist(val id: Int, val name: String): OutgoingMessage()

    @Serializable
    @SerialName("restart-or-previous")
    data object RestartOrPrevious: OutgoingMessage()

    @Serializable
    @SerialName("seek-backward")
    data object SeekBackward: OutgoingMessage()

    @Serializable
    @SerialName("seek-forward")
    data object SeekForward: OutgoingMessage()

    @Serializable
    @SerialName("seek-to")
    data class SeekTo(val seekTo: Int): OutgoingMessage()

    @Serializable
    @SerialName("send-active-music")
    data class SendActivePlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("send-all-playlists")
    data object SendAllPlaylists: OutgoingMessage()

    @Serializable
    @SerialName("send-music")
    data object SendPlaylist: OutgoingMessage()

    @Serializable
    @SerialName("set-current-music")
    data class SetCurrentPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("shuffle-all-playlists")
    data object ShuffleAllPlaylists: OutgoingMessage()

    @Serializable
    @SerialName("shuffle-music")
    data object ShufflePlaylist: OutgoingMessage()

    @Serializable
    @SerialName("stop")
    data object Stop: OutgoingMessage()

    @Serializable
    @SerialName("stop-after-current")
    data object StopAfterCurrent: OutgoingMessage()

    @Serializable
    @SerialName("volume")
    data class Volume(val volume: Int): OutgoingMessage()

    @Serializable
    @SerialName("volume-down")
    data object VolumeDown: OutgoingMessage()

    @Serializable
    @SerialName("volume-up")
    data object VolumeUp: OutgoingMessage()
}