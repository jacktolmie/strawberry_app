package com.example.strawberry_app.network

import java.security.MessageDigest

fun generateProof(nonceBase64: String, password: String): String {
    val nonceBytes = android.util.Base64.decode(nonceBase64, android.util.Base64.DEFAULT)
    val combined = nonceBytes + password.toByteArray(Charsets.UTF_8)
    val digest = MessageDigest.getInstance("SHA-256").digest(combined)
    return digest.joinToString("") { "%02x".format(it) }
}