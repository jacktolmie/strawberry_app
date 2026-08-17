package com.example.strawberry_app.screens.navigation

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun NavRail(
    windowSizeClass: WindowSizeClass,
    pagerState: PagerState
){


}
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(name = "Phone – Portrait", showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,orientation=portrait")
@Composable
fun NavRailPreviewPhone() {
    val windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(411.dp, 891.dp))
    NavRail(
        windowSizeClass = windowSizeClass,
        pagerState = PagerState { 3 }
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(name = "Tablet – Landscape", showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape")
@Composable
fun NavRailPreviewTablet() {
    val windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(1280.dp, 800.dp))
    NavRail(
        windowSizeClass = windowSizeClass,
        pagerState = PagerState { 3 }
    )
}