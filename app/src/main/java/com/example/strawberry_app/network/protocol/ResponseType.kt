package com.example.strawberry_app.network.protocol

import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.music.SongInfo
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
            "deleted_playlist_with_id" -> ResponseType.DeletedPlaylistWithId.serializer()
            "is_playlist_a_favourite" -> ResponseType.IsPlaylistAFavourite.serializer()
            "playlist_closed" -> ResponseType.PlaylistClosed.serializer()
            "removed_duplicates_from_playlist" -> ResponseType.RemovedDuplicatesFromPlaylist.serializer()
            "removed_unavailable_songs" -> ResponseType.RemoveUnavailableSongs.serializer()
            "rename_playlist" -> ResponseType.RenamePlaylist.serializer()
            "removed_songs_from_playlist" -> ResponseType.RemovedSongFromPlaylist.serializer()
            "running_command" -> ResponseType.RunningCommand.serializer()
            "sent_active_playlist" -> ResponseType.SentActivePlaylist.serializer()
            "sent_album_cover" -> ResponseType.SentAlbumCover.serializer()
            "sent_requested_playlist" -> ResponseType.SendRequestedPlaylist.serializer()
            "set_current_playlist_to" -> ResponseType.SetActivePlaylistTo.serializer()
            "shuffled_playlist" -> ResponseType.ShuffledPlaylist.serializer()
            "shuffled_all_playlists" -> ResponseType.ShuffledALlPlaylists.serializer()
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
    @SerialName("deleted_playlist_with_id")
    data class DeletedPlaylistWithId(val id: Long = -1L): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("is_playlist_a_favourite")
    data class IsPlaylistAFavourite(
        val id: Long = -1L,
        @SerialName("is_favourite")
        val isFavourite: Boolean = false): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("playlist_closed")
    data class PlaylistClosed(val id: Long): ResponseType()


    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("removed_duplicates_from_playlist")
    data object RemovedDuplicatesFromPlaylist: ResponseType()

    @Serializable
    @SerialName("removed_unavailable_songs")
    data object RemoveUnavailableSongs: ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("rename_playlist")
    data class RenamePlaylist(
        val id: Long,
        val name: String = ""): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("removed_songs_from_playlist")
    data class RemovedSongFromPlaylist(val id: Long = -1L): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("running_command")
    data class RunningCommand(val command: String = ""): ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("sent_active_playlist")
    data class SentActivePlaylist(val id: Long = -1L): ResponseType()

    @Serializable
    @SerialName("sent_album_cover")
    data object SentAlbumCover: ResponseType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("send_requested_playlist")
    data class SendRequestedPlaylist(val playlist: Playlist): ResponseType()
    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("set_current_playlist_to")
    data class SetActivePlaylistTo(val id: Long = -1L): ResponseType()

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
    @SerialName("songs")
    data class Songs(
        val songsInfo: List<SongInfo>
    ): ResponseType()
}
