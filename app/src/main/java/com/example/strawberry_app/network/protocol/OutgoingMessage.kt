package com.example.strawberry_app.network.protocol

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("command")
sealed class OutgoingMessage {

    @Serializable
    @SerialName("clear-playlist")
    data class ClearPlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("close-playlist")
    data class CloseCurrent(val id: Int) : OutgoingMessage()

    @Serializable
    @SerialName("delete-current-playlist")
    data class DeleteCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("favourite-playlist")
    data class FavouritePlaylist(val playlistId: Long, val favourite: Boolean): OutgoingMessage()

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
    @SerialName("remove-current-songs-playlist")
    data class RemoveCurrentSongFromPlaylist(val playlistId: Long, val songIndex: Long): OutgoingMessage()

    @Serializable
    @SerialName("remove-duplicates-playlist")
    data object RemoveDuplicatesFromPlaylist: OutgoingMessage()

    @Serializable
    @SerialName("rename-playlist")
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
    data class SeekTo(val seekTo: Long): OutgoingMessage()

    @Serializable
    @SerialName("send-active-playlist")
    data class SendActivePlaylist(val id: Int): OutgoingMessage()

    @Serializable
    @SerialName("send-all-playlists")
    data object SendAllPlaylists: OutgoingMessage()

    @Serializable
    @SerialName("send-current-playlist")
    data class SendPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("set-current-playlist")
    data class SetCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("shuffle-all-playlists")
    data object ShuffleAllPlaylists: OutgoingMessage()

    @Serializable
    @SerialName("shuffle-playlist")
    data class ShufflePlaylist(val id: Long): OutgoingMessage()

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