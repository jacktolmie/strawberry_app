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
            NavRail(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("MyApp navrail with height was called")
        }
        windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) && !isPortrait -> {
            NavRail(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("MyApp no nav or rail with height was called")
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) && !isPortrait -> {
            // Tablet in landscape
            NavRail(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("MyApp navrail with width was called")
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) && isPortrait -> {
            // Tablet in portrait
            NavBar(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("MyApp navbar with width was called")
        }
        else -> {
            // Phone in portrait
            NavBar(
                windowSizeClass = windowSizeClass,
                pagerState = pagerState
            )
            println("MyApp navbar 'else' was called")
        }
    }
}