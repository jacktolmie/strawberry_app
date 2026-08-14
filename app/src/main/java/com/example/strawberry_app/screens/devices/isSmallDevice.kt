package com.example.strawberry_app.screens.devices

fun isSmallDevice(deviceTypesBreakdown: DeviceTypesBreakdown) =
    deviceTypesBreakdown == DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE ||
    deviceTypesBreakdown == DeviceTypesBreakdown.SMALL_PHONE_PORTRAIT
