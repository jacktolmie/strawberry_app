package com.example.strawberry_app.network.protocol

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object IncomingMessageSerializer : JsonContentPolymorphicSerializer<IncomingMessage>(IncomingMessage::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<IncomingMessage> {
        return when (element.jsonObject["type"]?.jsonPrimitive?.content) {
            "error" -> ErrorType.serializer()
            "event" -> EventType.serializer()
            "response" -> ResponseType.serializer()
            "auth" -> AuthType.serializer()
            else -> throw SerializationException("Unknown type: $element")
        }
    }
}

@Serializable(with = IncomingMessageSerializer::class)
sealed class IncomingMessage{

    @Serializable
    @SerialName("auth")
    data object Auth: IncomingMessage()

    @Serializable
    @SerialName("error")
    data class Error(
        val error: String
    ): IncomingMessage()

    @Serializable
    @SerialName("event")
    data class Event(
        val event: String
    ): IncomingMessage()

    @Serializable
    @SerialName("response")
    data class Response(
        val response: String
    ): IncomingMessage()
}
// ErrorType, EventType, ResponseType are defined in files by those names.