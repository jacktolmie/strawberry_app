package com.example.strawberry_app.network.protocol

import com.example.strawberry_app.playlist.Playlist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
//@SerialName("event")
sealed class EventType: IncomingMessage() {
    @Serializable
    @SerialName("active_playlist")
    data class ActivePlaylist(val id: Int, val row: Int? = null): EventType()

    @Serializable
    @SerialName("favourite_playlist")
    data class FavouritePlaylist(val id: Int, val favourite: Boolean): EventType()

    @Serializable
    @SerialName("make_all_playlists")
    data class MakeAllPlaylists(val playlists: List<Playlist>): EventType()

    @Serializable
    @SerialName("make_current_playlist")
    data class MakeCurrentPlaylist(val playlist: Playlist): EventType()

    @Serializable
    @SerialName("pause")
    data class Pause(val time: Long): EventType()

    @Serializable
    @SerialName("play")
    data class Play(val time: Long, val row: Int? = null): EventType()

    @Serializable
    @SerialName("rename_playlist")
    data class RenamePlaylist(val id: Int, val name: String): EventType()

    @Serializable
    @SerialName("seek_backward")
    data class SeekBackward(val time: Long): EventType()

    @Serializable
    @SerialName("seek_forward")
    data class SeekForward(val time: Long): EventType()

    @Serializable
    @SerialName("seek_to")
    data class SeekTo(val time: Long): EventType()

    @Serializable
    @SerialName("song_changed")
    data class SongChanged(val track_id: Int): EventType()

    @Serializable
    @SerialName("stop")
    object Stop: EventType()

    @Serializable
    @SerialName("volume_changed")
    data class VolumeChanged(val volume: Int): EventType()
}