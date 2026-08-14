package com.example.strawberry_app.screens.devices

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.window.core.layout.WindowSizeClass
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker

@Composable
fun detectDevice(windowSizeClass: WindowSizeClass): DeviceTypesBreakdown {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    // Check for foldable state
    val context = LocalContext.current
    val windowLayoutInfo = WindowInfoTracker
        .getOrCreate(context)
        .windowLayoutInfo(context as Activity)
        .collectAsState(initial = null)

    val foldingFeature = windowLayoutInfo.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()
        ?.firstOrNull()

    // Foldable checks first since they are most specific
    if (foldingFeature != null) {
        return when {
            foldingFeature.state == FoldingFeature.State.HALF_OPENED -> {
                DeviceTypesBreakdown.FOLDABLE_HALF_OPEN
            }
            foldingFeature.state == FoldingFeature.State.FLAT && isPortrait -> {
                DeviceTypesBreakdown.FOLDABLE_OPEN_PORTRAIT
            }
            foldingFeature.state == FoldingFeature.State.FLAT && !isPortrait -> {
                DeviceTypesBreakdown.FOLDABLE_OPEN_LANDSCAPE
            }
            else -> {
                // Folded closed, acts like a phone
                DeviceTypesBreakdown.FOLDABLE_CLOSED
            }
        }
    }

    // Tablet checks — using safe thresholds between your phone and tablet dp values
    if (isPortrait) {
        if (windowSizeClass.isWidthAtLeastBreakpoint(750) &&
            windowSizeClass.isHeightAtLeastBreakpoint(1000)) {
            return DeviceTypesBreakdown.TABLET_PORTRAIT
        }
    } else {
        if (windowSizeClass.isWidthAtLeastBreakpoint(1000) &&
            windowSizeClass.isHeightAtLeastBreakpoint(750)) {
            return DeviceTypesBreakdown.TABLET_LANDSCAPE
        }
    }

// Phone checks
    return when {
        isPortrait && !windowSizeClass.isHeightAtLeastBreakpoint(800) -> {
            DeviceTypesBreakdown.SMALL_PHONE_PORTRAIT
        }
        !isPortrait && !windowSizeClass.isHeightAtLeastBreakpoint(400) -> {
            DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE
        }
        isPortrait -> {
            DeviceTypesBreakdown.PHONE_PORTRAIT
        }
        else -> {
            DeviceTypesBreakdown.PHONE_LANDSCAPE
        }
    }
}