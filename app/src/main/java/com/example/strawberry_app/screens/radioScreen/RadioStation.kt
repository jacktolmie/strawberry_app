package com.example.strawberry_app.screens.radioScreen

enum class RadioStation(val values: String) {
    BITRATE("bitrate"),
    CLICKCOUNT("clickCount"),
    FORMAT("codec"),
    COUNTRY("country"),
    LANGUAGE("language"),
    NAME("name"),
    GENRE("tags"),
    VOTES("votes")
}

enum class RadioSource (val source: String) {
    RADIOBROWSER("radioBrowser"),
    RADIOPARADISE("radioParadise"),
    SOMAFM("somaFm");

    companion object{
        fun fromString(source: String): RadioSource? {
            return entries.find{ it.source == source}
        }
    }
}