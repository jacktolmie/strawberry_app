package com.example.strawberry_app.screens.playlistScreen

enum class RepeatModeValues(val repeatValue: String) {
    ALBUM("album"),
    OFF("off"),
    PLAYLIST("playlist"),
    TRACK("track"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): RepeatModeValues =
            entries.find { it.repeatValue == value.lowercase() } ?: UNKNOWN
    }
}