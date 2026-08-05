package com.example.strawberry_app.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.network.ConnectionViewModel
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute
import com.example.strawberry_app.ui.theme.icons.music_note
import com.example.strawberry_app.ui.theme.icons.queue_music
import com.example.strawberry_app.ui.theme.icons.settings
import kotlinx.coroutines.launch


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

@Composable
fun NavIcon(image: ImageVector, description: Int){
    Icon(
        imageVector = image,
        contentDescription = stringResource(description)
    )
}

@Preview
@Composable
fun NavBarPreview(){
    NavBar()
}