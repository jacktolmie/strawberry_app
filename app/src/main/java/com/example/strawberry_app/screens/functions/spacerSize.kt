package com.example.strawberry_app.screens.functions

import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown

fun spacerSize (deviceTypesBreakdown: DeviceTypesBreakdown) = if (
        deviceTypesBreakdown == DeviceTypesBreakdown.PHONE_LANDSCAPE ||
        deviceTypesBreakdown == DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE
    ) 0.dp else 10.dp
