package com.example.strawberry_app.music

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class Playlist(
    val id: Long,
    val favourite: Boolean,
    val name: String,
    val songs: List<SongInfo>,
    @SerialName("playlist_size")
    val playlistSize: Long,
    @SerialName("playlist_length")
    val playlistLength: Long
)

/*
var showDialog by remember { mutableStateOf(false) }
var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

if (showDialog) {
    Alert(
        action = R.string.some_string,
        onConfirm = {
            pendingAction?.invoke()
            showDialog = false
        },
        onDismiss = { showDialog = false }
    )
}
 */