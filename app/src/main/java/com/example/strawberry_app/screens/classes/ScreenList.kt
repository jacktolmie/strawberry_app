package com.example.strawberry_app.screens.classes

sealed class Screen(val route: String) {
    object Player : Screen("player")
    object Playlist : Screen("playlist")
    object Radio : Screen("radio")
    object RadioBrowser: Screen("radioBrowser")
    object RadioParadise: Screen("radioParadise")
    object Settings : Screen("settings")
    object SomaFm: Screen("somaFm")
    object RadioGraph: Screen("radioGraph")
}