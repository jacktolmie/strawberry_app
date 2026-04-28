package com.example.strawberry_app.network

import java.security.MessageDigest

fun generateProof(nonceHex: String, password: String): String {
    val nonceBytes = nonceHex.chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()

    val combined = nonceBytes + password.toByteArray(Charsets.UTF_8)
    val digest = MessageDigest.getInstance("SHA-256").digest(combined)

    return digest.joinToString("") { "%02x".format(it) }
}