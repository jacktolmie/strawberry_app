package com.example.strawberry_app

import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass
import com.example.strawberry_app.network.ConnectionViewModel
import com.example.strawberry_app.screens.navigation.NavBar
import com.example.strawberry_app.screens.navigation.NavRail

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    when {
        !windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND) && !isPortrait -> {
            // Phone in landscape (height drops below 480dp when rotated)
            NavRail()
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) && !isPortrait -> {
            // Tablet in landscape
            NavRail()
        }
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) && isPortrait -> {
            // Tablet in portrait
            NavBar()
        }
        else -> {
            // Phone in portrait
            NavBar()
        }
    }
}