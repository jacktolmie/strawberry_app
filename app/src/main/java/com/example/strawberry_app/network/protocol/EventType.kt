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
//            "current_playlist" -> EventType.CurrentPlaylist.serializer()
            "favourite_playlist" -> EventType.FavouritePlaylist.serializer()
            "gui_updates" -> EventType.GuiUpdates.serializer()
            "make_all_playlists" -> EventType.MakeAllPlaylists.serializer()
            "make_current_playlist" -> EventType.MakeCurrentPlaylist.serializer()
            "next" -> EventType.Next.serializer()
            "pause" -> EventType.Pause.serializer()
            "play" -> EventType.Play.serializer()
            "previous" -> EventType.Previous.serializer()
            "rename_playlist" -> EventType.RenamePlaylist.serializer()
            "seek_backward" -> EventType.SeekBackward.serializer()
            "seek_forward" -> EventType.SeekForward.serializer()
            "seek_to" -> EventType.SeekTo.serializer()
            "song_changed" -> EventType.SongChanged.serializer()
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
        val id: Int,
        val row: Long? = null
    ) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("favourite_playlist")
    data class FavouritePlaylist(
        val id: Int,
        val favourite: Boolean
    ) : EventType()


    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("gui_updates")
    data class GuiUpdates(
        val active_playlist: Int,
        val current_playlist: Int,
        val current_song: Long,
        val time: Long = 0,
        val playing: String = "",
        val volume: Int = 0,
        val playlists: MakeAllPlaylists? = null
    ) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("make_all_playlists")
    data class MakeAllPlaylists(val playlists: List<Playlist>) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("make_current_playlist")
    data class MakeCurrentPlaylist(
        @SerialName("current_playlist") val playlist: Playlist) : EventType()

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
        val active_playlist: Int = 0,
        val length: Long = 0L,
        val row: Long? = null,
        val time: Long = 0L
    ) : EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("previous")
    data object Previous: EventType()

    @Serializable
    @JsonIgnoreUnknownKeys
    @SerialName("rename_playlist")
    data class RenamePlaylist(val id: Int, val name: String) : EventType()

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
    data class SongChanged(val track_id: Long = 0) : EventType()

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