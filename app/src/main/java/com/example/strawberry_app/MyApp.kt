package com.example.strawberry_app

import android.content.res.Configuration
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass
import com.example.strawberry_app.screens.navigation.NavBar
import com.example.strawberry_app.screens.navigation.ScreenType

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val pagerState = rememberPagerState(pageCount = { 3 })
    println("MyApp Width: ${configuration.screenWidthDp}dp, Height: ${configuration.screenHeightDp}dp, isPortrait: $isPortrait")
    when {
        isPortrait && !windowSizeClass.isHeightAtLeastBreakpoint(600) -> {
            NavBar(
                isPortrait = isPortrait,
                screenType = ScreenType.SMALL_PHONE,
                pagerState = pagerState,
                showLabel = false
            )
            println("MyApp navBar under 450 called. Portrait mode")
        }

        isPortrait && !windowSizeClass.isHeightAtLeastBreakpoint(1000) -> {
            NavBar(
                isPortrait = isPortrait,
                screenType = ScreenType.MEDIUM_PHONE,
                pagerState = pagerState,
                showLabel = true
            )
            println("MyApp navBar under height dp medium called. Portrait mode")
        }

        !isPortrait && !windowSizeClass.isHeightAtLeastBreakpoint(400) -> {
            NavBar(
                isPortrait = isPortrait,
                screenType = ScreenType.SMALL_PHONE,
                pagerState = pagerState,
                showLabel = false
            )
            println("MyApp navBar under 450 called. Portrait mode")
        }

        !isPortrait &&!windowSizeClass.isHeightAtLeastBreakpoint(600) -> {
            NavBar(
                isPortrait = isPortrait,
                screenType = ScreenType.MEDIUM_PHONE,
                pagerState = pagerState,
                showLabel = true
            )
            println("MyApp navBar under height dp medium called. Portrait mode")
        }

        else -> {
            println("myapp large screen called")
        }
    }
}