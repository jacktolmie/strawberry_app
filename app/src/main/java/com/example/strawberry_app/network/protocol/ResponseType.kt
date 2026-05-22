package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("response")
sealed  class ResponseType: IncomingMessage() {

    @Serializable
    @SerialName("cleared_playlist")
    data class ClearedPlaylist(val name: String = ""): ResponseType()

    @Serializable
    @SerialName("closed_playlist_with_id")
    data class ClosedPlaylistWithId(val id: Int = 0): ResponseType()

    @Serializable
    @SerialName("deleted_playlist_with_id")
    data class DeletedPlaylistWithId(val id: Int = 0): ResponseType()

    @Serializable
    @SerialName("is_playlist_a_favourite")
    data class IsPlaylistAFavourite(val is_favourite: Boolean = false): ResponseType()

    @Serializable
    @SerialName("playlist_closed")
    data object PlaylistClosed: ResponseType()


    @Serializable
    @SerialName("removed_duplicates_from_playlist")
    object RemovedDuplicatesFromPlaylist: ResponseType()

    @Serializable
    @SerialName("rename_playlist")
    data class RenamePlaylist(val name: String = ""): ResponseType()

    @Serializable
    @SerialName("removed_song_from_playlist")
    data class RemovedSongFromPlaylist(val name: String = ""): ResponseType()

    @Serializable
    @SerialName("running_command")
    data class RunningCommand(val command: String = ""): ResponseType()

    @Serializable
    @SerialName("sent_active_playlist")
    data class SentActivePlaylist(val id: Int = 0): ResponseType()

    @Serializable
    @SerialName("set_current_playlist_to")
    data class SetActivePlaylistTo(val name: String = ""): ResponseType()

    @Serializable
    @SerialName("shuffled_playlist")
    object ShuffledPlaylist: ResponseType()

    @Serializable
    @SerialName("shuffled_all_playlists")
    object ShuffledALlPlaylists: ResponseType()

    @Serializable
    @SerialName("song_info")
    data class SongInfo(
        val id: Int = 0,
        val artist: String = "",
        val album: String = "",
        val title: String = "",
        val length: Long = 0
        //val coverImage // Unknown for now.
    ): ResponseType()
}
