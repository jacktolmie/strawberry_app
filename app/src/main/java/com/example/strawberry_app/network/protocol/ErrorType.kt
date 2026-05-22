package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("error")
sealed class ErrorType: IncomingMessage() {

    @Serializable
    @SerialName("command_not_found")
    data class CommandNotFound(val command: String = ""): ErrorType()

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