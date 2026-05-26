package com.example.strawberry_app.screens

sealed class ScreenList(val route: String) {
    data object Player: ScreenList("player")
    data object Playlist: ScreenList("music")
    data object Settings: ScreenList("settings")
}