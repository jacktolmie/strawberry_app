package com.example.strawberry_app.network.protocol

import com.example.strawberry_app.music.Playlist
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("command")
sealed class OutgoingMessage {

    // Playlist commands
    @Serializable
    @SerialName("clear-playlist")
    data class ClearPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("close-playlist")
    data class CloseCurrent(val id: Long) : OutgoingMessage()

    @Serializable
    @SerialName("delete-playlist")
    data class DeleteCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("favourite-playlist")
    data class FavouritePlaylist(val id: Long, val favourite: Boolean): OutgoingMessage()

    @Serializable
    @SerialName("remove-duplicates-playlist")
    data class RemoveDuplicatesFromPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("remove-songs-playlist")
    data class RemoveCurrentSongsFromPlaylist(
        val id: Long,
        @SerialName("songs_list")
        val songsList: List<Long>): OutgoingMessage()

    @Serializable
    @SerialName("rename-playlist")
    data class RenamePlaylist(val id: Long, val name: String): OutgoingMessage()

    @Serializable
    @SerialName("request-cover")
    data object RequestCover: OutgoingMessage()
    @Serializable
    @SerialName("send-playlist-song")
    data class SendActivePlaylistSong(val id: Long, val songIndex: Long): OutgoingMessage()

    @Serializable
    @SerialName("send-all-playlists")
    data class SendAllPlaylists(val playlists: List<Playlist>): OutgoingMessage()

    @Serializable
    @SerialName("send-playlist")
    data class SendCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("set-current-playlist")
    data class SetCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("shuffle-all-playlists")
    data object ShuffleAllPlaylists: OutgoingMessage()

    @Serializable
    @SerialName("shuffle-current-playlist")
    data class ShuffleCurrentPlaylist(val id: Long): OutgoingMessage()

    // Player controls
    @Serializable
    @SerialName("mute")
    data object Mute: OutgoingMessage()

    @Serializable
    @SerialName("next")
    data object Next: OutgoingMessage()

//    @Serializable
//    @SerialName("pause")
//    data object Pause: OutgoingMessage()

    @Serializable
    @SerialName("play-pause")
    data object PlayPause: OutgoingMessage()

//    @Serializable
//    @SerialName("play")
//    data object Play: OutgoingMessage()

    @Serializable
    @SerialName("previous")
    data object Previous: OutgoingMessage()

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
    data class SeekTo(@SerialName("seek-to")val seekTo: Long): OutgoingMessage()

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