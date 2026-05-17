package com.example.strawberry_app.network

import android.util.Log
import com.example.strawberry_app.network.protocol.AuthType
import com.example.strawberry_app.server.ServerInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

private fun readAuthMessage(dataInputStream: DataInputStream): AuthType {
    return try{
        val length = dataInputStream.readInt()
        if (length !in 1..10000) return AuthType.Error("Invalid message length $length")

        val messageBytes = ByteArray(length)
        dataInputStream.readFully(messageBytes)
        val jsonString = String(messageBytes, Charsets.UTF_8)

        val json = Json { classDiscriminator = "auth"; ignoreUnknownKeys = true }
        json.decodeFromString<AuthType>(jsonString)

    } catch (e: Exception){
        AuthType.Error("Failed to read auth message: ${e.message}")
    }
}

fun authenticate(
    serverInfo: ServerInfo,
    socket: Socket,
    dataInputStream: DataInputStream,
    dataOutputStream: DataOutputStream
): Boolean {
    return when (val response = readAuthMessage(dataInputStream)) {

        is AuthType.Success -> true

        is AuthType.Failure -> false

        is AuthType.Challenge -> {
            val proof = generateProof(response.nonce, serverInfo.password)
            val proofJson = """{"proof": "$proof"}"""

            // Send proof using length-prefixed framing
            val messageBytes = proofJson.toByteArray(Charsets.UTF_8)
            dataOutputStream.writeInt(messageBytes.size)
            dataOutputStream.write(messageBytes)
            dataOutputStream.flush()

            // Read the AUTH_SUCCESS or AUTH_FAILED response
            readAuthMessage(dataInputStream) is AuthType.Success
        }

        is AuthType.Error -> false
    }


}
//private fun parseAuthMessage(message: String?): AuthType {
//    if( message.isNullOrEmpty()) return AuthType.Error("Empty message from server")
//
//    val json = Json{ classDiscriminator = "auth"}
//    val response = json.decodeFromString<AuthType>(message)
//
//    return when (response){
//        is AuthType.Challenge -> {
//            AuthType.Challenge(response.nonce)
//        }
//        is AuthType.Error -> {
//            AuthType.Error("Error with server message: $message")
//        }
//        is AuthType.Failure -> {
//            AuthType.Failure
//        }
//        is AuthType.Success -> {
//            AuthType.Success
//        }
//    }
//}
//
//fun authenticate(
//    serverInfo: ServerInfo,
//    socket: Socket
//    dataInputStream: DataInputStream,
//    dataOutputStream: DataOutputStream
//): Boolean {
//
//    val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//    val writer = socket.getOutputStream()
//
////    val json = Json{ classDiscriminator = "auth"}
////    val response = json.decodeFromString<AuthType>(reader.readLine())
//
//    return when (val response = parseAuthMessage(reader.readLine())) {
//
//        is AuthType.Failure -> { false }
//
//        is AuthType.Success -> { true }
//
//        is AuthType.Challenge -> {
//            val proof = generateProof(response.nonce, serverInfo.password)
//            val command = "PROOF $proof\n"
//
//            writer.write(command.toByteArray(Charsets.UTF_8))
//            writer.flush()
//
//            parseAuthMessage(reader.readLine()) is AuthType.Success
//        }
//
//        is AuthType.Error -> {
//            false
//        }
//    }
//}