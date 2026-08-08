package com.example.strawberry_app.screens.playlistScreen

enum class ShuffleModeValues(val shuffleValue: String) {
    ALL("all"),
    CURRENT("current"),
    OFF("off"),
    UNKNOWN("unknown");

    companion object {
        fun fromString(value: String): ShuffleModeValues =
            entries.find { it.shuffleValue == value.lowercase() } ?: UNKNOWN
    }
}