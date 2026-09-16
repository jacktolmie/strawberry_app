package com.example.strawberry_app.screens.classes

import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown

data class DeviceState(
    val isPortrait: Boolean = true,
    val isTablet: Boolean = false,
    val showTopBar: Boolean = true,
    val deviceType: DeviceTypesBreakdown = DeviceTypesBreakdown.PHONE_PORTRAIT
)
