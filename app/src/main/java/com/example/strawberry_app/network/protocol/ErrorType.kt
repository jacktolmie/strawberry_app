package com.example.strawberry_app.network.protocol

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ErrorTypeSerializer : JsonContentPolymorphicSerializer<ErrorType>(ErrorType::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ErrorType> {
        return when (element.jsonObject["error"]?.jsonPrimitive?.content) {
            "command_not_found" -> ErrorType.CommandNotFound.serializer()
            "cover_not_found" -> ErrorType.CoverNotFound.serializer()
            "not_enough_arguments_passed_needs" -> ErrorType.NotEnoughArguments.serializer()
            "playlist_not_closed" -> ErrorType.PlaylistNotClosed.serializer()
            "playlist_not_found" -> ErrorType.PlaylistNotFound.serializer()
            "wrong_argument_sent" -> ErrorType.WrongArgumentSent.serializer()
            else -> throw SerializationException("Unknown error type: $element")
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializable(with = ErrorTypeSerializer::class)
sealed class ErrorType: IncomingMessage() {

    @Serializable
    @SerialName("command_not_found")
    data class CommandNotFound(val command: String = ""): ErrorType()

    @Serializable
    @SerialName("cover_not_found")
    data object CoverNotFound: ErrorType()

    @Serializable
    @SerialName("not_enough_arguments_passed_needs")
    data class NotEnoughArguments(val required: Int = 0): ErrorType()

    @Serializable
    @SerialName("playlist_not_closed")
    data object PlaylistNotClosed: ErrorType()

    @Serializable
    @SerialName("playlist_not_found")
    data class PlaylistNotFound(val name: String = ""): ErrorType()

    @Serializable
    @SerialName("wrong_argument_sent")
    data class WrongArgumentSent(val argument: String = ""): ErrorType()
}