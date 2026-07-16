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

object EventTypeSerializer : JsonContentPolymorphicSerializer<EventType>(EventType::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<EventType> {
        return when (element.jsonObject["event"]?.jsonPrimitive?.content) {
            "active_playlist" -> EventType.ActivePlaylist.serializer()
            "closed_playlist_with_id" -> EventType.ClosedPlaylistWithId.serializer()
            "cover_image" -> EventType.CoverImage.serializer()
            "favourite_playlist" -> EventType.FavouritePlaylist.serializer()
            "gui_updates" -> EventType.GuiUpdates.serializer()
            "make_all_playlists" -> EventType.MakeAllPlaylists.serializer()
            "make_playlist" -> EventType.MakePlaylist.serializer()
            "next" -> EventType.Next.serializer()
            "pause" -> EventType.Pause.serializer()
            "play" -> EventType.Play.serializer()
            "previous" -> EventType.Previous.serializer()
            "rename_playlist" -> EventType.RenamePlaylist.serializer()
            "repeat_mode" -> EventType.RepeatMode.serializer()
            "seek_backward" -> EventType.SeekBackward.serializer()
            "seek_forward" -> EventType.SeekForward.serializer()
            "seek_to" -> EventType.SeekTo.serializer()
            "song_changed" -> EventType.SongChanged.serializer()
            "song_info" -> EventType.SongInfo.serializer()
            "stop" -> EventType.Stop.serializer()
            "time" -> EventType.Time.serializer()
            "volume" -> EventType.Volume.serializer()
            "volume_changed" -> EventType.VolumeChanged.serializer()
            else -> throw SerializationException("Unknown event type: $element")
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable(with = EventTypeSerializer::class)
sealed class EventType: IncomingMessage() {
    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("active_playlist")
    data class ActivePlaylist(
        val id: Long,
        val row: Long = -1L
    ) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("closed_playlist_with_id")
    data class ClosedPlaylistWithId(val id: Long = 0L): EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("cover_image")
    data class CoverImage(
        val name: String = "",
        @SerialName("cover_image")
        val coverImage: String = ""
    ): EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("favourite_playlist")
    data class FavouritePlaylist(
        val id: Long,
        val favourite: Boolean
    ) : EventType()


    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("gui_updates")
    data class GuiUpdates(
        @SerialName("active_playlist")
        val activePlaylist: Long = -1L,
        @SerialName("cover_image")
        val coverImage: String = "",
        @SerialName("current_playlist")
        val currentPlaylist: Long = -1L,
        @SerialName("current_song")
        val currentSong: Long = -1L,
        val playing: String = "",
        val playlists: MakeAllPlaylists? = null,
        @SerialName("repeat_mode")
        val repeatMode: String = "",
        val time: Long = 0L,
        val volume: Int = 0
    ) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("make_playlist")
    data class MakePlaylist(
        val playlist: Playlist) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("make_all_playlists")
    data class MakeAllPlaylists(val playlists: List<Playlist>) : EventType()



    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("next")
    data object Next: EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("pause")
    data class Pause(val time: Long) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("play")
    data class Play(
        @SerialName("active_playlist")
        val activePlaylist: Long = 0L,
        val length: Long = 0L,
        val row: Long = -1L,
        val time: Long = 0L
    ) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("previous")
    data object Previous: EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("rename_playlist")
    data class RenamePlaylist(val id: Long, val name: String) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("repeat_mode")
    data class RepeatMode(val id: Long, @SerialName("repeat_mode") val repeatMode: String): EventType()
    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("seek_backward")
    data class SeekBackward(val time: Long) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("seek_forward")
    data class SeekForward(val time: Long) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("seek_to")
    data class SeekTo(val time: Long) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("song_changed")
    data class SongChanged(
        val row: Long
//        @SerialName("track_id")
//        val trackId: Long = 0L
    ) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("song_info")
    data class SongInfo(
        val artist: String = "",
        val album: String = "",
        @SerialName("cover_image")
        val coverImage: String = "",
        val id: Long = 0L,
        val length: Long = 0L,
        val title: String = "",
        @SerialName("song_url")
        val url: String = "",

    ): EventType()
    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("stop")
    data object Stop : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("time")
    data class Time(val time: Long): EventType()
    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("volume")
    data class Volume(val volume: Int = 0): EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("volume_changed")
    data class VolumeChanged(val volume: Int = 0) : EventType()
}