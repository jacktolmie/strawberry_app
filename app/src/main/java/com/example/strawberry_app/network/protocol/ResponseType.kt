package com.example.strawberry_app.network.protocol

import com.example.strawberry_app.music.Playlist
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonIgnoreUnknownKeys
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ResponseTypeSerializer : JsonContentPolymorphicSerializer<ResponseType>(ResponseType::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ResponseType> {
        return when (element.jsonObject["response"]?.jsonPrimitive?.content) {
            "cleared_playlist" -> ResponseType.ClearedPlaylist.serializer()
            "closed_playlist_with_id" -> ResponseType.ClosedPlaylistWithId.serializer()
            "deleted_playlist_with_id" -> ResponseType.DeletedPlaylistWithId.serializer()
            "is_playlist_a_favourite" -> ResponseType.IsPlaylistAFavourite.serializer()
            "playlist_closed" -> ResponseType.PlaylistClosed.serializer()
            "removed_duplicates_from_playlist" -> ResponseType.RemovedDuplicatesFromPlaylist.serializer()
            "rename_playlist" -> ResponseType.RenamePlaylist.serializer()
            "removed_song_from_playlist" -> ResponseType.RemovedSongFromPlaylist.serializer()
            "running_command" -> ResponseType.RunningCommand.serializer()
            "sent_active_playlist" -> ResponseType.SentActivePlaylist.serializer()
            "sent_requested_playlist" -> ResponseType.SendRequestedPlaylist.serializer()
            "set_current_playlist_to" -> ResponseType.SetActivePlaylistTo.serializer()
            "shuffled_playlist" -> ResponseType.ShuffledPlaylist.serializer()
            "shuffled_all_playlists" -> ResponseType.ShuffledALlPlaylists.serializer()
            "song_info" -> ResponseType.SongInfo.serializer()
            "songs" -> ResponseType.Songs.serializer()
            else -> throw SerializationException("Unknown response type: $element")
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable(with = ResponseTypeSerializer::class)
sealed  class ResponseType: IncomingMessage() {

    @Serializable
    @SerialName("cleared_playlist")
    data class ClearedPlaylist(val name: String = ""): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("closed_playlist_with_id")
    data class ClosedPlaylistWithId(val id: Int = 0): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("deleted_playlist_with_id")
    data class DeletedPlaylistWithId(val id: Int = 0): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("is_playlist_a_favourite")
    data class IsPlaylistAFavourite(val is_favourite: Boolean = false): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("playlist_closed")
    data object PlaylistClosed: ResponseType()


    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("removed_duplicates_from_playlist")
    object RemovedDuplicatesFromPlaylist: ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("rename_playlist")
    data class RenamePlaylist(val name: String = ""): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("removed_song_from_playlist")
    data class RemovedSongFromPlaylist(val name: String = ""): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("running_command")
    data class RunningCommand(val command: String = ""): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("sent_active_playlist")
    data class SentActivePlaylist(val id: Int = 0): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("send_requested_playlist")
    data class SendRequestedPlaylist(val playlist: Playlist): ResponseType()
    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("set_current_playlist_to")
    data class SetActivePlaylistTo(val name: String = ""): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("shuffled_playlist")
    object ShuffledPlaylist: ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("shuffled_all_playlists")
    object ShuffledALlPlaylists: ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("song_info")
    data class SongInfo(
        val id: Int = 0,
        val artist: String = "",
        val album: String = "",
        val title: String = "",
        val length: Long = 0
        //val coverImage // Unknown for now.
    ): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("songs")
    data class Songs(
        val songInfos: List<com.example.strawberry_app.music.SongInfo>
    ): ResponseType()
}
