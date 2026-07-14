package com.example.strawberry_app.network.protocol

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("auth")
sealed class AuthType: IncomingMessage() {
    @Serializable
    @SerialName("auth_failed")
    data object AuthFailed : AuthType()

    @Serializable
    @SerialName("auth_success")
    data object AuthSuccess : AuthType()

    @Serializable
    @SerialName("challenge")
    data class Challenge(
        val nonce: String = ""
    ) : AuthType()

    @Serializable
    @SerialName("error")
    data class Error(
        val message: String
    ) : AuthType()
}