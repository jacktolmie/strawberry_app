package com.example.strawberry_app.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.strawberry_app.screens.classes.Screen
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.radioScreen.RadioRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute

data class RadioSources(
    val onNavigateToSomaFm: () -> Unit = {},
    val onNavigateToRadioParadise: () -> Unit = {},
    val onNavigateToRadioBrowser: () -> Unit = {},
)

@Composable
fun AppNavHost(
    navController: NavHostController,
    deviceType: DeviceTypesBreakdown,
    isPortrait: Boolean,
    isTablet: Boolean,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = if (isTablet) Screen.Playlist.route else Screen.Player.route,
        modifier = modifier
    ) {
        if(!isTablet){
            composable(Screen.Player.route) {
                PlayerRoute(
                    isPortrait = isPortrait,
                    deviceType = deviceType,
                    onNavigateToSettings = onNavigateToSettings
                )
            }
        }
        composable(Screen.Playlist.route) {
            PlaylistRoute(
                isPortrait = isPortrait,
                deviceType = deviceType,
                onNavigateToSettings = onNavigateToSettings
            )
        }
        navigation(startDestination = Screen.Radio.route, route = Screen.RadioGraph.route){
            composable(Screen.Radio.route) {
                RadioRoute(
                    isPortrait = isPortrait,
                    deviceType = deviceType,
                    radioSource = RadioSources(
                        onNavigateToSomaFm = { navController.navigate(Screen.SomaFm.route) },
                        onNavigateToRadioParadise = { navController.navigate(Screen.RadioParadise.route) },
                        onNavigateToRadioBrowser = { navController.navigate(Screen.RadioBrowser.route) },
                    ),
                    onNavigateToSettings = onNavigateToSettings
                )
            }
        }
        composable(Screen.Settings.route) {
            SettingsRoute(
                isPortrait = isPortrait,
                deviceType = deviceType,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}