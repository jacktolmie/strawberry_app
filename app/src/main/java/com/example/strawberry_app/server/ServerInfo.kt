package com.example.strawberry_app.server

import kotlinx.coroutines.flow.StateFlow

//data class ServerInfo(
//    val ip: String = "",
//    val port: Int = 5000,
//    val password: String = ""
//)

data class ServerInfo (
    val ip: String,
    val port: Int,
    val password: String
)