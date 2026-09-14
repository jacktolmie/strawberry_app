package com.example.strawberry_app.screens.classes

sealed class Screen(val route: String) {
    object Player : Screen("player")
    object Playlist : Screen("playlist")
    object Radio : Screen("radio")
    object Settings : Screen("settings")
}