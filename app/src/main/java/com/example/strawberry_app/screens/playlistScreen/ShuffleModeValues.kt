package com.example.strawberry_app.screens.playlistScreen

enum class ShuffleModeValues(val shuffleValue: String) {
    ALBUMS("albums"),
    ALL("all"),
    GROUPING("grouping"),
    OFF("off"),
    TRACKS("tracks"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): ShuffleModeValues =
            entries.find { it.shuffleValue == value.lowercase() } ?: UNKNOWN
    }
}