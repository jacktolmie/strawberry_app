package com.example.strawberry_app.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.computeWindowSizeClass
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute
import com.example.strawberry_app.ui.theme.icons.music_note
import com.example.strawberry_app.ui.theme.icons.queue_music
import com.example.strawberry_app.ui.theme.icons.settings
import kotlinx.coroutines.launch

@Composable
fun NavBar(
    windowSizeClass: WindowSizeClass,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = pagerState.currentPage == 0,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) }},
                    icon = { NavIcon(music_note, R.string.navbar_player) },
                    label = {Text(text= stringResource(R.string.navbar_player))}
                )

                NavigationBarItem(
                    selected = pagerState.currentPage == 1,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) }},
                    icon = { NavIcon(queue_music, R.string.navbar_playlist) },
                    label = { Text(text = stringResource(R.string.navbar_playlist))}
                )

                NavigationBarItem(
                    selected = pagerState.currentPage == 2,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(2) }},
                    icon = { NavIcon(settings, R.string.navbar_settings) },
                    label = { Text(stringResource(R.string.navbar_settings))}
                )
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            modifier = Modifier.padding(paddingValues),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> PlayerRoute(windowSizeClass)
                1 -> PlaylistRoute(windowSizeClass)
                2 -> SettingsRoute(windowSizeClass)
            }
        }
    }
}

@Preview
@Composable
fun NavBarPreview(){
    val windowSizeClass =
        WindowSizeClass.BREAKPOINTS_V1.computeWindowSizeClass(widthDp = 400f, heightDp = 800f)
    NavBar(
        windowSizeClass = windowSizeClass,
        pagerState = PagerState { 3 }
    )
}