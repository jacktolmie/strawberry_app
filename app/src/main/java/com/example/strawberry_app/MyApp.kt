package com.example.strawberry_app

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.devices.DeviceTypes
import com.example.strawberry_app.screens.devices.detectDevice
import com.example.strawberry_app.screens.devices.getDeviceType
import com.example.strawberry_app.screens.devices.isSmallDevice
import com.example.strawberry_app.screens.navigation.NavBar
import com.example.strawberry_app.screens.navigation.NavIcon
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute
import com.example.strawberry_app.ui.theme.icons.music_note
import com.example.strawberry_app.ui.theme.icons.queue_music
import com.example.strawberry_app.ui.theme.icons.settings
import kotlinx.coroutines.launch

@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val coroutineScope = rememberCoroutineScope()
    val deviceType = detectDevice(windowSizeClass = windowSizeClass)
    var selectedIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 3 })

    val navItems: NavigationSuiteScope.() -> Unit = {
        item(
            selected = selectedIndex == 0,
            onClick = {
                selectedIndex = 0
                coroutineScope.launch { pagerState.animateScrollToPage(0) }
            },
            icon = { NavIcon(music_note, R.string.navbar_player) },
            label = { if (!isSmallDevice(deviceType)) Text(stringResource(R.string.navbar_player)) }
        )
        item(
            selected = selectedIndex == 1,
            onClick = {
                selectedIndex = 1
                coroutineScope.launch { pagerState.animateScrollToPage(1) }
            },
            icon = { NavIcon(queue_music, R.string.navbar_playlist) },
            label = { if (!isSmallDevice(deviceType)) Text(stringResource(R.string.navbar_playlist)) }
        )
        item(
            selected = selectedIndex == 2,
            onClick = {
                selectedIndex = 2
                coroutineScope.launch { pagerState.animateScrollToPage(2) }
            },
            icon = { NavIcon(settings, R.string.navbar_settings) },
            label = { if (!isSmallDevice(deviceType)) Text(stringResource(R.string.navbar_settings)) }
        )
    }

    when (getDeviceType(deviceType)) {
        DeviceTypes.PHONE, DeviceTypes.FOLDABLE_CLOSED -> {
            NavBar(
                isPortrait = isPortrait,
                pagerState = pagerState,
                deviceType = deviceType,
                showLabel = !isSmallDevice(deviceType)// deviceType != DeviceTypesBreakdown.SMALL_PHONE_LANDSCAPE
            )

            }

        DeviceTypes.TABLET -> {
            println("myapp devicetype tablet called")
            NavigationSuiteScaffold(navigationSuiteItems = navItems) {
                if(isPortrait){
                    Column(modifier = Modifier.fillMaxWidth()) {
                        PlayerRoute(
                            modifier = Modifier.weight(1F),
                            isPortrait = isPortrait,
                            deviceType = deviceType
                        )
                        HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
                        when (selectedIndex) {
                            2 -> SettingsRoute(
                                modifier = Modifier.weight(1F),
                                isPortrait = isPortrait,
                                deviceType = deviceType
                            )
                            else -> PlaylistRoute(
                                modifier = Modifier.weight(1F),
                                isPortrait = isPortrait,
                                deviceType = deviceType
                            )
                        }
                    }
                } else {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        PlayerRoute(
                            modifier = Modifier.weight(1F),
                            isPortrait = isPortrait,
                            deviceType = deviceType
                        )
                        VerticalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
                        when (selectedIndex) {
                            2 -> SettingsRoute(
                                modifier = Modifier.weight(1F),
                                isPortrait = isPortrait,
                                deviceType = deviceType
                            )
                            else -> PlaylistRoute(
                                modifier = Modifier.weight(1F),
                                isPortrait = isPortrait,
                                deviceType = deviceType
                            )
                        }
                    }
                }
            }
        }

        DeviceTypes.FOLDABLE -> {
            // Placeholder
        }
    }
}