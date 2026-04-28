package com.example.strawberry_app.network

import kotlinx.serialization.json.JsonObject
import java.io.DataOutputStream

fun sendMessage(outputStream: DataOutputStream, json: JsonObject)  {
    val messageString = json.toString()
    val messageBytes = messageString.toByteArray(Charsets.UTF_8)

    // Delete when done
    println("MessageByte: $messageBytes and messageString: $messageString")

    outputStream.writeInt(messageBytes.size)
    outputStream.write(messageBytes)
    outputStream.flush()
}