package com.example.strawberry_app

import android.content.res.Configuration
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass
import com.example.strawberry_app.screens.devices.DeviceTypes
import com.example.strawberry_app.screens.devices.detectDevice
import com.example.strawberry_app.screens.devices.getDeviceType
import com.example.strawberry_app.screens.devices.isSmallDevice
import com.example.strawberry_app.screens.navigation.NavBar
import com.example.strawberry_app.screens.navigation.NavRail

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val pagerState = rememberPagerState(pageCount = { 3 })
    println("MyApp Width: ${configuration.screenWidthDp}dp, Height: ${configuration.screenHeightDp}dp, isPortrait: $isPortrait")

    val deviceType = detectDevice(windowSizeClass = windowSizeClass)
    println("myapp device $deviceType is small? ${isSmallDevice(deviceType)}")
    when (getDeviceType(deviceType)) {
        DeviceTypes.PHONE -> {
            NavBar(
                isPortrait = isPortrait,
                pagerState = pagerState,
                deviceType = deviceType,
                showLabel = !isSmallDevice(deviceType)
            )
        }

        DeviceTypes.TABLET-> {
            NavRail(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("myapp tablet called")
        }

        DeviceTypes.FOLDABLE -> {
            println("myapp foldable called")
        }
    }
}