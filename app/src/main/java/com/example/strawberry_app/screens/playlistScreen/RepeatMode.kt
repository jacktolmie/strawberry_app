package com.example.strawberry_app.screens.playlistScreen

import java.util.Locale.getDefault

enum class RepeatMode(val serverValue: String) {
    ALBUM("album"),
    OFF("off"),
    PLAYLIST("playlist"),
    TRACK("track"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): RepeatMode =
            entries.find { it.serverValue == value.lowercase() } ?: UNKNOWN
    }
}