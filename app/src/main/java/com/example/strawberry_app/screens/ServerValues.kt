package com.example.strawberry_app.screens

import com.example.strawberry_app.music.SongInfo

enum class PlayState{
    PLAYING, PAUSED, STOPPED
}
data class ServerValues(
    val activePlaylist: Int = -1,
    val currentPlaylist: Int = -1,
    val currentSong: SongInfo = SongInfo(),
    val currentSongId: Long = -1,
    val currentTime: Long = 0L,
    val playState: PlayState = PlayState.STOPPED,
    val volume: Int = 0
)
