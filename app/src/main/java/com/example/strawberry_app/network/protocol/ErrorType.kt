package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("error")
sealed class ErrorType: IncomingMessage() {

    @Serializable
    @SerialName("command_not_found")
    data class CommandNotFound(val command: String): ErrorType()

    @Serializable
    @SerialName("not_enough_arguments_passed_needs")
    data class NotEnoughArguments(val required: Int): ErrorType()

    @Serializable
    @SerialName("playlist_not_found")
    data class PlaylistNotFound(val required: Int): ErrorType()

    @Serializable
    @SerialName("wrong_argument_sent")
    data class WrongArgumentSent(val required: String): ErrorType()
}