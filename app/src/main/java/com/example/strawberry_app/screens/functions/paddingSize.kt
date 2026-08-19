package com.example.strawberry_app.screens.functions

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown

@Composable
fun bottomPadding(deviceType: DeviceTypesBreakdown) = when (deviceType) {
    DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE -> 5.dp
    DeviceTypesBreakdown.PHONE_LANDSCAPE -> 10.dp
    DeviceTypesBreakdown.TABLET_PORTRAIT -> 20.dp
    else -> WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
}