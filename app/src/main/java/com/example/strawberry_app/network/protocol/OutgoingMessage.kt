package com.example.strawberry_app.network.protocol

import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.screens.radioScreen.StreamInfo
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
    @SerialName("clear_playlist")
    data class ClearPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("close_playlist")
    data class CloseCurrent(val id: Long) : OutgoingMessage()

    @Serializable
    @SerialName("delete_playlist")
    data class DeleteCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("favourite_playlist")
    data class FavouritePlaylist(val id: Long, val favourite: Boolean): OutgoingMessage()

    // Send currently selected playlist id and song index to server
    @Serializable
    @SerialName("remote_sent_active")
    data class RemoteSentActive(
        val id: Long,
        @SerialName("song_index")
        val songIndex: Long): OutgoingMessage()

    @Serializable
    @SerialName("remote_changed_playlist")
    data class RemoteChangedPlaylist(
        val id: Long,
        @SerialName("from_index")
        val fromIndex: Long,
        @SerialName("to_index")
        val toIndex: Long
    ): OutgoingMessage()

    @Serializable
    @SerialName("remove_duplicates_playlist")
    data class RemoveDuplicatesFromPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("remove_songs_playlist")
    data class RemoveCurrentSongsFromPlaylist(
        val id: Long,
        @SerialName("songs_list")
        val songsList: List<Long>): OutgoingMessage()

    @Serializable
    @SerialName("remove_unavailable_songs")
    data class RemoveUnavailableSongs(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("rename_playlist")
    data class RenamePlaylist(val id: Long, val name: String): OutgoingMessage()

    @Serializable
    @SerialName("repeat_mode")
    data class RepeatMode(
        @SerialName("repeat_mode")
        val repeatMode: String
    ): OutgoingMessage()

    @Serializable
    @SerialName("request_cover")
    data class RequestCover(
        @SerialName("cover_art")
        val coverArt: String
    ): OutgoingMessage()

    @Serializable
    @SerialName("send_active_playlist_song")
    // Cannot find a way to not use data classes to send commands. Sends "" if not a data class
    data class SendActivePlaylistSong(val test: Boolean = true) : OutgoingMessage()

    @Serializable
    @SerialName("send_all_playlists")
    data class SendAllPlaylists(val playlists: List<Playlist>): OutgoingMessage()

    @Serializable
    @SerialName("send_playlist")
    data class SendCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("send_stations")
    data class SendStations(val source: String, val streams: List<StreamInfo>): OutgoingMessage()
    @Serializable
    @SerialName("set_current_playlist")
    data class SetCurrentPlaylist(val id: Long): OutgoingMessage()

    @Serializable
    @SerialName("shuffle_current_playlist")
    data class ShuffleCurrentPlaylist(val id: Long): OutgoingMessage()
    @Serializable
    @SerialName("shuffle_mode")
    data class ShuffleMode(
        @SerialName("shuffle_mode")
        val shuffleMode: String
    ): OutgoingMessage()

    // Player controls
    @Serializable
    @SerialName("mute")
    data object Mute: OutgoingMessage()

    @Serializable
    @SerialName("next")
    data object Next: OutgoingMessage()

    @Serializable
    @SerialName("play_pause")
    data object PlayPause: OutgoingMessage()

    @Serializable
    @SerialName("previous")
    data object Previous: OutgoingMessage()

    @Serializable
    @SerialName("restart_or_previous")
    data object RestartOrPrevious: OutgoingMessage()

    @Serializable
    @SerialName("seek_backward")
    data object SeekBackward: OutgoingMessage()

    @Serializable
    @SerialName("seek_forward")
    data object SeekForward: OutgoingMessage()

    @Serializable
    @SerialName("seek_to")
    data class SeekTo(
        @SerialName("seek_to")
        val seekTo: Long
    ): OutgoingMessage()

    @Serializable
    @SerialName("stop")
    data object Stop: OutgoingMessage()

    @Serializable
    @SerialName("stop_after_current")
    data object StopAfterCurrent: OutgoingMessage()

    @Serializable
    @SerialName("volume")
    data class Volume(val volume: Int): OutgoingMessage()

    @Serializable
    @SerialName("volume_down")
    data object VolumeDown: OutgoingMessage()

    @Serializable
    @SerialName("volume_up")
    data object VolumeUp: OutgoingMessage()
}