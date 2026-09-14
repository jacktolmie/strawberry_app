package com.example.strawberry_app

import android.content.res.Configuration
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.strawberry_app.screens.classes.NavItemData
import com.example.strawberry_app.screens.classes.Screen
import com.example.strawberry_app.screens.devices.DeviceTypes
import com.example.strawberry_app.screens.devices.detectDevice
import com.example.strawberry_app.screens.devices.getDeviceType
import com.example.strawberry_app.screens.devices.isDevicePhone
import com.example.strawberry_app.screens.devices.isDeviceTablet
import com.example.strawberry_app.screens.devices.isSmallDevice
import com.example.strawberry_app.screens.navigation.AppNavHost
import com.example.strawberry_app.screens.navigation.NavIcon
import com.example.strawberry_app.screens.navigation.TabletScaffold
import com.example.strawberry_app.ui.theme.icons.music_note
import com.example.strawberry_app.ui.theme.icons.queue_music
import com.example.strawberry_app.ui.theme.icons.radio
import com.example.strawberry_app.ui.theme.icons.settings

@Composable
fun MyApp(windowSizeClass: WindowSizeClass) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val deviceType = detectDevice(windowSizeClass = windowSizeClass)
    val isSmallDeviceCheck = isSmallDevice(deviceType)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val isTablet = isDeviceTablet(deviceType)
    val isPhone = isDevicePhone(deviceType)
    val settingsScreen = { navController.navigate(Screen.Settings.route) }

    val navItems = listOf(
        NavItemData(music_note, R.string.navbar_player, Screen.Player),
        NavItemData(queue_music, R.string.navbar_playlist, Screen.Playlist),
        NavItemData(radio, R.string.navbar_radio, Screen.Radio),
        NavItemData(settings, R.string.navbar_settings, Screen.Settings)
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navItems.forEach { navItem ->
                // If device is a tablet, skip adding Player screen option.
                if (navItem.screen == Screen.Player && isTablet) return@forEach
                // If device is a phone, skip Settings screen option.
                if (navItem.screen == Screen.Settings && isPhone) return@forEach

                item(
                    selected = currentDestination == navItem.screen.route,
                    onClick = {
                        navController.navigate(navItem.screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { NavIcon(navItem.iconVector, navItem.labelRes) },
                    label = { if (!isSmallDeviceCheck) Text(stringResource(navItem.labelRes)) }
                )
            }
        }
    ) {
        when (getDeviceType(deviceType)) {
            DeviceTypes.TABLET -> TabletScaffold(
                navController = navController,
                deviceType = deviceType,
                isTablet = isTablet,
                isPortrait = isPortrait,
                onNavigateToSettings = settingsScreen
            )

            else -> AppNavHost(
                navController = navController,
                deviceType = deviceType,
                isPortrait = isPortrait,
                isTablet = isTablet,
                onNavigateToSettings = settingsScreen
            )
        }
    }
}