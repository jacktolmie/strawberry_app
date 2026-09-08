package com.example.strawberry_app.screens.radioScreen.composables

import com.example.strawberry_app.data.dao.StationWithStreams
import com.example.strawberry_app.screens.radioScreen.RadioScreenState
import com.example.strawberry_app.screens.radioScreen.RadioState

val previewRadioScreenState = RadioScreenState(
    albumArtCollection = emptyMap(),
    radioState = RadioState(
        activeStation = "Groove Salad",
        activeStream = "https://ice2.somafm.com/groovesalad-256-mp3"
    ),
    stations = listOf(
        StationWithStreams(
            bitrate = 256,
            clickCount = 1500,
            country = "US",
            description = "A nicely chilled plate of ambient/electronica.",
            donate = "https://somafm.com/donate/",
            format = "mp3",
            genre = listOf("Ambient", "Electronica"),
            homepage = "https://somafm.com/groovesalad/",
            id = "groovesalad",
            image = "https://api.somafm.com/img/groovesalad120.png",
            language = "English",
            name = "Groove Salad",
            quality = "High",
            stationSource = "somafm",
            stationUrl = "https://somafm.com/groovesalad/",
            streamUrl = "https://ice2.somafm.com/groovesalad-256-mp3",
            votes = 2500
        ),
        StationWithStreams(
            bitrate = 128,
            clickCount = 800,
            country = "US",
            description = "Ambient music for sleeping and relaxing.",
            donate = "https://somafm.com/donate/",
            format = "aac",
            genre = listOf("Ambient", "Sleep"),
            homepage = "https://somafm.com/dronezone/",
            id = "dronezone",
            image = "https://api.somafm.com/img/dronezone120.png",
            language = "English",
            name = "Drone Zone",
            quality = "Medium",
            stationSource = "somafm",
            stationUrl = "https://somafm.com/dronezone/",
            streamUrl = "https://ice2.somafm.com/dronezone-128-aac",
            votes = 1200
        )
    ),
    stationSource = listOf("somafm", "radioparadise"),
    sortedStations = emptyList()
)