package com.example.strawberry_app.screens.navigation

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.computeWindowSizeClass

@Composable
fun NavRail(
    windowSizeClass: WindowSizeClass,
    pagerState: PagerState
){


}

@Preview
@Composable
fun NavRailPreview(){
    val windowSizeClass =
        WindowSizeClass.BREAKPOINTS_V1.computeWindowSizeClass(widthDp = 900f, heightDp = 400f)
    NavRail(
        windowSizeClass = windowSizeClass,
        pagerState = PagerState { 3 }
    )
}