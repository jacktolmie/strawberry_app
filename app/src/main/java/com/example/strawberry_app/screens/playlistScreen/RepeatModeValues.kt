package com.example.strawberry_app.screens.playlistScreen

enum class RepeatModeValues(val repeatValue: String) {
    ALBUM("album"),
    INTRO("intro"),
    OFF("off"),
    PLAYLIST("playlist"),
    STOP("stop"),
    TRACK("track"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): RepeatModeValues =
            entries.find { it.repeatValue == value.lowercase() } ?: UNKNOWN

        fun toString(value: RepeatModeValues): String =
            when (value) {
                ALBUM -> "Album"
                INTRO -> "Intro"
                OFF -> "Off"
                PLAYLIST -> "Playlist"
                STOP -> "Stop after each track"
                TRACK -> "Track"
                UNKNOWN -> "Unknown"
            }
    }
}