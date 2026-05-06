package com.example.strawberry_app.network.protocol

import kotlinx.serialization.Serializable

@Serializable
sealed class IncomingMessage
// ErrorType, EventType, PlaylistType, ResponseType are defined in files by those names.