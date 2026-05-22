package com.example.strawberry_app.network

import android.util.Log
import com.example.strawberry_app.network.protocol.AuthType
import com.example.strawberry_app.server.ServerInfo
import kotlinx.io.IOException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

private const val MAX_MESSAGE_LENGTH = 10_000

private val authJson = Json {
    ignoreUnknownKeys = true
}
private fun readAuthMessage(dataInputStream: DataInputStream): AuthType {
    return try{
        val length = dataInputStream.readInt()
        if (length !in 1..MAX_MESSAGE_LENGTH) return AuthType.Error("Invalid message length $length")

        val messageBytes = ByteArray(length)
        dataInputStream.readFully(messageBytes)
        val jsonString = String(messageBytes, Charsets.UTF_8)

        authJson.decodeFromString<AuthType>(jsonString)

    } catch (e: IOException) {
        AuthType.Error("Failed to read auth message: ${e.message}")
    } catch (e: SerializationException) {
        AuthType.Error("Failed to parse auth message: ${e.message}")
    }
}

@Serializable
private data class ProofMessage(val proof: String)

fun authenticate(
    serverInfo: ServerInfo,
    socket: Socket,
    dataInputStream: DataInputStream,
    dataOutputStream: DataOutputStream
): Boolean {
    return when (val response = readAuthMessage(dataInputStream)) {

        is AuthType.AuthSuccess -> true
        is AuthType.AuthFailed -> false
        is AuthType.Challenge -> {
            val proof = generateProof(response.nonce, serverInfo.password)

            val messageBytes = authJson.encodeToString(ProofMessage(proof)).toByteArray(Charsets.UTF_8)
            dataOutputStream.writeInt(messageBytes.size)
            dataOutputStream.write(messageBytes)
            dataOutputStream.flush()

            when (val result = readAuthMessage(dataInputStream)) {
                is AuthType.AuthSuccess -> true
                is AuthType.Error -> {
                    Log.e("Auth", "Challenge response error: ${result.message}")
                    false
                }
                else -> false
            }
        }
        is AuthType.Error -> false
    }
}