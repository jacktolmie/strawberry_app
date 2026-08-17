package com.example.strawberry_app.screens.devices

import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
@Composable
fun detectDevice(windowSizeClass: WindowSizeClass): DeviceTypesBreakdown {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val context = LocalContext.current

    // Foldable checks first — physical bounds are fine here since
    // foldable hinge detection is always about the real display
    val windowLayoutInfo = WindowInfoTracker
        .getOrCreate(context)
        .windowLayoutInfo(context)
        .collectAsState(initial = null)

    val foldingFeature = windowLayoutInfo.value?.displayFeatures
        ?.filterIsInstance<FoldingFeature>()
        ?.firstOrNull()

    if (foldingFeature != null) {
        return when {
            foldingFeature.state == FoldingFeature.State.HALF_OPENED ->
                DeviceTypesBreakdown.FOLDABLE_HALF_OPEN
            foldingFeature.state == FoldingFeature.State.FLAT && isPortrait ->
                DeviceTypesBreakdown.FOLDABLE_OPEN_PORTRAIT
            foldingFeature.state == FoldingFeature.State.FLAT && !isPortrait ->
                DeviceTypesBreakdown.FOLDABLE_OPEN_LANDSCAPE
            else ->
                DeviceTypesBreakdown.FOLDABLE_CLOSED
        }
    }

    // Everything else uses windowSizeClass, which already reflects
    // the actual space your app has in split-screen
    val widthClass = windowSizeClass. widthSizeClass
    val heightClass = windowSizeClass.heightSizeClass

    return when {
        // Tablet: expanded width, or medium width with non-compact height
        // (covers 7" tablets that sit below the EXPANDED threshold)
        widthClass == WindowWidthSizeClass.Expanded ->
            if (isPortrait) DeviceTypesBreakdown.TABLET_PORTRAIT
            else DeviceTypesBreakdown.TABLET_LANDSCAPE

        widthClass == WindowWidthSizeClass.Medium &&
                heightClass != WindowHeightSizeClass.Compact  ->
            if (isPortrait) DeviceTypesBreakdown.TABLET_PORTRAIT
            else DeviceTypesBreakdown.TABLET_LANDSCAPE

        // Phone landscape (compact width + compact height)
        !isPortrait && heightClass == WindowHeightSizeClass.Compact ->
            DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE

        !isPortrait ->
            DeviceTypesBreakdown.PHONE_LANDSCAPE

        // Phone portrait
        heightClass == WindowHeightSizeClass.Compact ->
            DeviceTypesBreakdown.SMALL_PHONE_PORTRAIT

        else ->
            DeviceTypesBreakdown.PHONE_PORTRAIT
    }
}