package com.example.strawberry_app

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute
import kotlinx.coroutines.launch
import com.example.strawberry_app.ui.theme.icons.settings
import com.example.strawberry_app.ui.theme.icons.music_note
import com.example.strawberry_app.ui.theme.icons.queue_music


@Composable
fun NavBar() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = pagerState.currentPage == 0,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) }},
                    icon = { music_note },
                    label = {Text(text="Player")}
                )

                NavigationBarItem(
                    selected = pagerState.currentPage == 1,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) }},
                    icon = { queue_music },
                    label = { Text("Playlists")}
                )

                NavigationBarItem(
                    selected = pagerState.currentPage == 2,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(2) }},
                    icon = { settings },
                    label = { Text("Settings")}
                )
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(paddingValues)
        ) { page ->
            when (page) {
                0 -> PlayerRoute()
                1 -> PlaylistRoute()
                2 -> SettingsRoute()
            }

        }

    }
}