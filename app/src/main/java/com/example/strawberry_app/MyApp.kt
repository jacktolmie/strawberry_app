package com.example.strawberry_app

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.strawberry_app.screens.devices.DeviceTypes
import com.example.strawberry_app.screens.devices.detectDevice
import com.example.strawberry_app.screens.devices.getDeviceType
import com.example.strawberry_app.screens.devices.isSmallDevice
import com.example.strawberry_app.screens.navigation.NavBar
import com.example.strawberry_app.screens.navigation.NavIcon
import com.example.strawberry_app.screens.navigation.NavItemData
import com.example.strawberry_app.screens.navigation.TabletScaffold
import com.example.strawberry_app.ui.theme.icons.music_note
import com.example.strawberry_app.ui.theme.icons.queue_music
import com.example.strawberry_app.ui.theme.icons.radio
import com.example.strawberry_app.ui.theme.icons.settings
import kotlinx.coroutines.launch

@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val coroutineScope = rememberCoroutineScope()
    val deviceType = detectDevice(windowSizeClass = windowSizeClass)
    var selectedIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { 4 })
    val isSmallDeviceCheck  = isSmallDevice(deviceType)

    val navItems = listOf(
        NavItemData(music_note, R.string.navbar_player),
        NavItemData(queue_music, R.string.navbar_playlist),
        NavItemData(radio, R.string.navbar_radio),
//        NavItemData(settings, R.string.navbar_settings)
    )

    val navSuiteItems: NavigationSuiteScope.() -> Unit = {
        navItems.forEachIndexed { index, navItem ->
            item(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                icon = { NavIcon(navItem.iconVector, navItem.labelRes) },
                label = { if (!isSmallDeviceCheck) Text(stringResource(navItem.labelRes)) }
            )
        }
    }

    NavigationSuiteScaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .statusBarsPadding().,
        navigationSuiteItems = navSuiteItems){

        when (getDeviceType(deviceType)) {
            DeviceTypes.PHONE, DeviceTypes.FOLDABLE_CLOSED -> {
                NavBar(
                    isPortrait = isPortrait,
                    pagerState = pagerState,
                    deviceType = deviceType,
//                    navItems = navItems,
//                    showLabel = isSmallDeviceCheck
                )

            }

            DeviceTypes.TABLET -> {
                println("myapp devicetype tablet called")
//                NavigationSuiteScaffold( navigationSuiteItems = navSuiteItems ) {
                    if(isPortrait){
                        Column(modifier = Modifier.fillMaxWidth()) {
                            TabletScaffold(
                                deviceType = deviceType,
                                isPortrait = isPortrait,
                                isRow = false,
                                selectedIndex = selectedIndex,
                                modifier = Modifier.weight(1F)
                            )
                        }
                    } else {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TabletScaffold(
                                deviceType = deviceType,
                                isPortrait = isPortrait,
                                isRow = true,
                                selectedIndex = selectedIndex,
                                modifier = Modifier.weight(1F)
                            )
                        }
                    }
//                }
            }

            DeviceTypes.FOLDABLE -> {
                // Placeholder
            }
        }
    }


}