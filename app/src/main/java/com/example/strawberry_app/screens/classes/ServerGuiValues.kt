package com.example.strawberry_app.screens.classes

import com.example.strawberry_app.music.SongInfo

enum class PlayState{
    PLAYING, PAUSED, STOPPED
}
data class ServerGuiValues(
    val coverImage: String = "",
    val activePlaylist: Long = -1L,
    val currentPlaylist: Long = -1L,
    val currentSong: SongInfo = SongInfo(),
    val currentSongId: Long = -1L,
    val currentTime: Long = 0L,
    val playState: PlayState = PlayState.STOPPED,
    val repeatMode: String = "",
    val shuffleMode: String = "",
    val totalAlbums: Int = 0,
    val totalArtists: Int = 0,
    val totalSongs: Int = 0,
    val volume: Int = 0
)
