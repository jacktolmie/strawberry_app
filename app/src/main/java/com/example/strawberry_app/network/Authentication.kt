package com.example.strawberry_app.network

import com.example.strawberry_app.server.ServerInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.InputStreamReader
import java.net.Socket

@Serializable
sealed class AuthResponse {

    @Serializable
    @SerialName("auth_success")
    data object Success : AuthResponse()

    @Serializable
    @SerialName("challenge")
    data class Challenge(
        val nonce: String
    ) : AuthResponse()

    @Serializable
    @SerialName("error")
    data class Error(
        val message: String
    ) : AuthResponse()
}

sealed class AuthMessage {
    object Success : AuthMessage()
    data class Challenge(val nonceHex: String) : AuthMessage()
    data class Error(val raw: String) : AuthMessage()
}

private fun parseAuthMessage(message: String?): AuthMessage {
    val trimmedMessage = message?.trim()

    return when{
        trimmedMessage == null -> AuthMessage.Error("No response")
        trimmedMessage.startsWith("AUTH_SUCCESS") -> AuthMessage.Success
        trimmedMessage.startsWith("CHALLENGE") ->
            AuthMessage.Challenge(message.substringAfter("CHALLENGE "))
        else -> AuthMessage.Error(message)
    }
}

suspend fun authenticate(
    serverInfo: ServerInfo,
//    inputStream: DataInputStream,
    socket: Socket
): Boolean {

    val reader = BufferedReader(InputStreamReader(withContext(Dispatchers.IO) {
        socket.getInputStream()
    }))
    val writer = withContext(Dispatchers.IO) {
        socket.getOutputStream()
    }
//    val reader = BufferedReader(InputStreamReader(inputStream))
//    val writer = withContext(Dispatchers.IO) {
//        socket.getOutputStream()
//    }

    return when (val message = parseAuthMessage(withContext(Dispatchers.IO) {
        reader.readLine()
    })) {

        is AuthMessage.Success -> {
            true
        }

        is AuthMessage.Challenge -> {
            val proof = generateProof(message.nonceHex, serverInfo.password)
            val command = "PROOF $proof\n"

            writer.write(command.toByteArray(Charsets.UTF_8))
            writer.flush()

            parseAuthMessage(reader.readLine()) is AuthMessage.Success
        }

        is AuthMessage.Error -> {
            false
        }
    }
}