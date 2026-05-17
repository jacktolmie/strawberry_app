package com.example.strawberry_app.network.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class AuthType {
    @Serializable
    @SerialName("AUTH_FAILED")
    data object Failure : AuthType()

    @Serializable
    @SerialName("AUTH_SUCCESS")
    data object Success : AuthType()

    @Serializable
    @SerialName("CHALLENGE")
    data class Challenge(
        val nonce: String = ""
    ) : AuthType()

    @Serializable
    @SerialName("ERROR")
    data class Error(
        val message: String
    ) : AuthType()
}