package com.example.strawberry_app.screens.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.composables.TopBar
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.devices.isSmallDevice
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.radioScreen.RadioRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute
import com.example.strawberry_app.ui.theme.icons.music_note
import com.example.strawberry_app.ui.theme.icons.queue_music
import com.example.strawberry_app.ui.theme.icons.radio
import com.example.strawberry_app.ui.theme.icons.settings
import kotlinx.coroutines.launch

@Composable
fun NavBar(
    isPortrait: Boolean,
    deviceType: DeviceTypesBreakdown,
//    navItems: List<NavItemData>,
    pagerState: PagerState,
//    showLabel: Boolean,
) {
//    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> PlayerRoute(isPortrait = isPortrait, deviceType = deviceType)
                1 -> PlaylistRoute(isPortrait = isPortrait, deviceType = deviceType)
                2 -> RadioRoute(isPortrait = isPortrait, deviceType = deviceType)
                3 -> SettingsRoute(isPortrait = isPortrait, deviceType = deviceType)
            }
        }
    }
}

@Preview
@Composable
fun NavBarPreview(){
    val navItems = listOf(
        NavItemData(music_note, R.string.navbar_player),
        NavItemData(queue_music, R.string.navbar_playlist),
        NavItemData(radio, R.string.navbar_radio),
        NavItemData(settings, R.string.navbar_settings)
    )
    NavBar(
        deviceType = DeviceTypesBreakdown.PHONE_PORTRAIT,
        pagerState = PagerState { 3 },
//        showLabel = true,
//        navItems = navItems,
        isPortrait = true,
//        paddingValues = PaddingValues()
    )
}


//        NavigationBar(
//            modifier = if (isSmallDevice(deviceType)) Modifier.height(60.dp) else Modifier
//        ) {
//            navItems.forEachIndexed { index, navItem ->
//                NavigationBarItem(
//                    selected = pagerState.currentPage == index,
//                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
//                    icon = { NavIcon(navItem.iconVector, navItem.labelRes) },
//                    label = { if (showLabel) Text(stringResource(navItem.labelRes)) }
//                )
//            }
//        }
//                NavigationBarItem(
//                    selected = pagerState.currentPage == 0,
//                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) }},
//                    icon = { NavIcon(music_note, R.string.navbar_player) },
//                    label = { if (showLabel) Text(text= stringResource(R.string.navbar_player))}
//                )
//
//                NavigationBarItem(
//                    selected = pagerState.currentPage == 1,
//                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) }},
//                    icon = { NavIcon(queue_music, R.string.navbar_playlist) },
//                    label = { if (showLabel) Text(text= stringResource(R.string.navbar_playlist))}
//                )
//
//                NavigationBarItem(
//                    selected = pagerState.currentPage == 2,
//                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(2) }},
//                    icon = { NavIcon(radio)},
//                    label = { if (showLabel) Text(text = stringResource(R.string.navbar_radio))}
//                )
//
//                NavigationBarItem(
//                    selected = pagerState.currentPage == 3,
//                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(3) }},
//                    icon = { NavIcon(settings, R.string.navbar_settings) },
//                    label = { if (showLabel) Text(text= stringResource(R.string.navbar_settings))}
//                )
//            }