package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class IncomingMessage{
    @Serializable
    @SerialName("error")
    data class Error(
        val error: String
//        ,val errorType: ErrorType
    ): IncomingMessage()

    @Serializable
    @SerialName("event")
    data class Event(
        val event: String
//        ,val eventType: EventType
    ): IncomingMessage()

    @Serializable
    @SerialName("response")
    data class Response(
        val response: String
//        ,val responseType: ResponseType
    ): IncomingMessage()
}
// ErrorType, EventType, PlaylistType, ResponseType are defined in files by those names.