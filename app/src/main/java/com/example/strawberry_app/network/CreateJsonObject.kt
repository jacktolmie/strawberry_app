package com.example.strawberry_app.network

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

fun createJsonObject(line: String): JsonObject {
    val parts = line.trim().split("\\s+".toRegex())
    if (parts.isEmpty()) return JsonObject(emptyMap())

    val command = parts[0]
    val args = parts.drop(1)
    val json = buildJsonObject {
        put("command", JsonPrimitive(command))
        when {
            args.size == 1 -> {
                put ("value", JsonPrimitive(args[0]))
            }
            args.isNotEmpty() -> {
                args.forEachIndexed { index, arg ->
                    put(index.toString(), JsonPrimitive(arg))
                }
            }
        }
    }
    println(json)
    return json
}