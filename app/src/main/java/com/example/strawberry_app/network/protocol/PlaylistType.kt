package com.example.strawberry_app.network.protocol

import com.example.strawberry_app.playlist.Playlist
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("playlist")
sealed class PlaylistType {

    @Serializable
    @SerialName("make_all_playlists")
    data class MakeAllPlaylists(val playlists: List<Playlist>): PlaylistType()

    @Serializable
    @SerialName("make_current_playlist")
    data class MakeCurrentPlaylist(val playlist: Playlist): PlaylistType()
}