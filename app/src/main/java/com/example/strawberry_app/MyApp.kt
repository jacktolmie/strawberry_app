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
    when (isPortrait) {
        !windowSizeClass.isHeightAtLeastBreakpoint(600) -> {
            NavBar(
                screenType = ScreenType.SMALL_PHONE,
                pagerState = pagerState,
                showLabel = false
            )
            println("MyApp navBar under 450 called. Portrait mode")
        }

        !windowSizeClass.isHeightAtLeastBreakpoint(1000) -> {
            NavBar(
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

//    when (!isPortrait) {
//
//    }
/*
        !windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) && !isPortrait -> {
            // Phone in landscape (height drops below 480dp when rotated)
            NavBar(
                isCompact = false,
                pagerState = pagerState,
                showLabel = false
            )
            println("MyApp navBar phone in landscape height mode was called")
        }
        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) && isPortrait -> {
            NavBar(
                isCompact = false,
                pagerState = pagerState,
                showLabel = true
            )
            println("MyApp navBar phone in portrait height mode was called")
        }

        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) && !isPortrait -> {
            NavRail(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("MyApp navRail tablet in landscape height was called")
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) && !isPortrait -> {
            // Tablet in landscape
            NavRail(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("MyApp navRail with width breakpoint in landscape was called")
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) && isPortrait -> {
            // Tablet in portrait
            NavBar(
                isCompact = false,
                pagerState = pagerState,
                showLabel = true
            )
            println("MyApp navbar with tablet width in portrait was called")
        }
        else -> {
            // Compact phone in portrait
            NavBar(
                isCompact = true,
                pagerState = pagerState,
                showLabel = false
            )
            println("MyApp navbar 'else' was called")
        }

 */
}