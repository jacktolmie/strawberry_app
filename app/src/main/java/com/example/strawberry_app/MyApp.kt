package com.example.strawberry_app

import android.content.res.Configuration
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass
import com.example.strawberry_app.screens.navigation.NavBar
import com.example.strawberry_app.screens.navigation.NavRail

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val pagerState = rememberPagerState(pageCount = { 3 })
    println("MyApp Width: ${configuration.screenWidthDp}dp, Height: ${configuration.screenHeightDp} isPortrait: $isPortrait")
    when {

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
    }
}