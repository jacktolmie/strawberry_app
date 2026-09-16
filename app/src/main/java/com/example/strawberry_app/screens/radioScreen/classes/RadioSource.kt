package com.example.strawberry_app.screens.radioScreen.classes

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