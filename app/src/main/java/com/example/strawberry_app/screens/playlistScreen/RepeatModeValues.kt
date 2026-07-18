package com.example.strawberry_app.screens.playlistScreen

enum class RepeatModeValues(val serverValue: String) {
    ALBUM("album"),
    OFF("off"),
    PLAYLIST("playlist"),
    TRACK("track"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): RepeatModeValues =
            entries.find { it.serverValue == value.lowercase() } ?: UNKNOWN
    }
}