package com.example.strawberry_app.screens.radioScreen.classes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("station_name")
data class RadioSourcesList(
    @SerialName("station_name")
    val stationName: String = "",
    val image: String = ""
)
