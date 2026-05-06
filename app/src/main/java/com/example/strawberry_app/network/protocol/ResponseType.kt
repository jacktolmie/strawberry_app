package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("response")
sealed  class ResponseType: IncomingMessage() {

    @Serializable
    @SerialName("cleared_playlist")
    data class ClearedPlaylist(val name: String): ResponseType()

    @Serializable
    @SerialName("closed_playlist_with_id")
    data class ClosedPlaylistWithId(val id: Int): ResponseType()

    @Serializable
    @SerialName("deleted_playlist_with_id")
    data class DeletedPlaylistWithId(val id: Int): ResponseType()

    @Serializable
    @SerialName("is_playlist_a_favourite")
    data class IsPlaylistAFavourite(val is_favourite: Boolean): ResponseType()

    @Serializable
    @SerialName("removed_duplicates_from_playlist")
    object RemovedDuplicatesFromPlaylist: ResponseType()

    @Serializable
    @SerialName("rename_playlist")
    data class RenamePlaylist(val name: String): ResponseType()

    @Serializable
    @SerialName("removed_song_from_playlist")
    data class RemovedSongFromPlaylist(val name: String): ResponseType()

    @Serializable
    @SerialName("running_command")
    data class RunningCommand(val command: String): ResponseType()

    @Serializable
    @SerialName("sent_active_playlist")
    data class SentActivePlaylist(val id: Int): ResponseType()

    @Serializable
    @SerialName("shuffled_playlist")
    object ShuffledPlaylist: ResponseType()

    @Serializable
    @SerialName("shuffled_all_playlists")
    object ShuffledALlPlaylists: ResponseType()


}
