package com.example.strawberry_app.server

data class SettingsUiState(
    val ip: String = "",
    val port: String = "",
    val password: String = "",

    val ipError: Int? = null,
    val portError: Int? = null,

    val isIpValid: Boolean = false,
    val isPortValid: Boolean = false,
    val hasChanged: Boolean = false,
    val enableSaveButton: Boolean = false,
)
