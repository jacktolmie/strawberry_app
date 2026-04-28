package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class IncomingMessage {

    @Serializable
    @SerialName("stop")
    object Stop: IncomingMessage()

    class Play: IncomingMessage()

}