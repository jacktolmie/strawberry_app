package com.example.strawberry_app.screens

sealed class ScreenList(val route: String) {
    data object Player: ScreenList("player")
    data object Playlist: ScreenList("playlist")
    data object Settings: ScreenList("settings")
}