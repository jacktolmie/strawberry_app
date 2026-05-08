package com.example.strawberry_app.network.decodeJson

import com.example.strawberry_app.network.protocol.IncomingMessage
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Envelope(val type: String)

fun DecodeJson(message: String): IncomingMessage{

    val json = Json{ ignoreUnknownKeys = false; classDiscriminator = "type"} //change ignoreUnknownKeys to true after testing
//    val base = json.decodeFromString<Envelope>(message)

//    when(base.type){
//        "error" -> {
//            IncomingMessage.Error(base)
//        }
//        "event" -> {
//
//        }
//        "response" -> {
//
//        }
//    }

//    return when (base.type) {
//        "error" -> IncomingMessage.Error(
//            error = base.message,
//            errorType = base.
//        )
//    }
return IncomingMessage.Error("test")
}