package com.example.strawberry_app.network

sealed class ConnectionState {
    object Connected : ConnectionState()
    object Connecting : ConnectionState()
    object Disconnected : ConnectionState()
    data class Error(val message: String, val canRetry: Boolean = true) : ConnectionState()
    data class Reconnecting(val attempt: Long, val time: Long): ConnectionState()
}
