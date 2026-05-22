package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("command")
sealed class OutgoingMessage {

    @Serializable
    @SerialName("close-playlist")
    data class CloseCurrent(val id: Int) : OutgoingMessage()

    @Serializable
    @SerialName("volume")
    data class Volume(val volume: Int): OutgoingMessage()

    @Serializable
    @SerialName("seek-to")
    data class SeekTo(val seekTo: Int): OutgoingMessage()

    @Serializable
    @SerialName("clear-playlist")
    data class ClearPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("delete-current-playlist")
    data class DeleteCurrentPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("favourite-playlist")
    data class FavouritePlaylist(val favourite: Boolean): OutgoingMessage()

    @Serializable
    @SerialName("remove-current-song-playlist")
    data class RemoveCurrentSongFromPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("rename-playlist")
    data class RenamePlaylist(val id: Int, val name: String): OutgoingMessage()

    @Serializable
    @SerialName("set-current-playlist")
    data class SetCurrentPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("send-active-playlist")
    data class SendActivePlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("Play")
    data object Play: OutgoingMessage()

    @Serializable
    @SerialName("play-pause")
    data object PlayPause: OutgoingMessage()

    @Serializable
    @SerialName("Pause")
    data object Pause: OutgoingMessage()

    @Serializable
    @SerialName("next")
    data object Next: OutgoingMessage()

    @Serializable
    @SerialName("stop")
    data object Stop: OutgoingMessage()

    @Serializable
    @SerialName("previous")
    data object Previous: OutgoingMessage()

    @Serializable
    @SerialName("stop-after-current")
    data object StopAfterCurrent: OutgoingMessage()

    @Serializable
    @SerialName("restart-or-previous")
    data object RestartOrPrevious: OutgoingMessage()

    @Serializable
    @SerialName("volume-up")
    data object VolumeUp: OutgoingMessage()

    @Serializable
    @SerialName("volume-down")
    data object VolumeDown: OutgoingMessage()

    @Serializable
    @SerialName("mute")
    data object Mute: OutgoingMessage()

    @Serializable
    @SerialName("seek-backward")
    data object SeekBackward: OutgoingMessage()

    @Serializable
    @SerialName("seek-forward")
    data object SeekForward: OutgoingMessage()

    @Serializable
    @SerialName("remove-duplicates-playlist")
    data object RemoveDuplicatesFromPlaylist: OutgoingMessage()

    @Serializable
    @SerialName("shuffle-playlist")
    data object ShufflePlaylist: OutgoingMessage()

    @Serializable
    @SerialName("shuffle-all-playlists")
    data object ShuffleAllPlaylists: OutgoingMessage()

    @Serializable
    @SerialName("send-playlist")
    data object SendPlaylist: OutgoingMessage()

    @Serializable
    @SerialName("send-all-playlists")
    data object SendAllPlaylists: OutgoingMessage()
}