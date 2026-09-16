package com.example.strawberry_app.screens.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.strawberry_app.screens.classes.Screen
import com.example.strawberry_app.screens.classes.DeviceState
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.radioScreen.RadioRoute
import com.example.strawberry_app.screens.radioScreen.sources.radiobrowser.RadioBrowserRoute
import com.example.strawberry_app.screens.radioScreen.sources.radioparadise.RadioParadiseRoute
import com.example.strawberry_app.screens.radioScreen.sources.somafm.SomaFmRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute

data class RadioSources(
    val onNavigateToSomaFm: () -> Unit = {},
    val onNavigateToRadioParadise: () -> Unit = {},
    val onNavigateToRadioBrowser: () -> Unit = {},
)

@Composable
fun AppNavHost(
    navController: NavHostController,
    deviceState: DeviceState,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = if (deviceState.isTablet) Screen.Playlist.route else Screen.Player.route,
        modifier = modifier
    ) {
        if(!deviceState.isTablet){
            composable(Screen.Player.route) {
                PlayerRoute(
                    deviceState = deviceState,
                    onNavigateToSettings = onNavigateToSettings,
                )
            }
        }
        composable(Screen.Playlist.route) {
            PlaylistRoute(
                deviceState = deviceState,
                onNavigateToSettings = onNavigateToSettings
            )
        }
        navigation(startDestination = Screen.Radio.route, route = Screen.RadioGraph.route){
            composable(Screen.Radio.route) {
                RadioRoute(
                    deviceState = deviceState,
                    radioSources = RadioSources(
                        onNavigateToSomaFm = { navController.navigate(Screen.SomaFm.route) },
                        onNavigateToRadioParadise = { navController.navigate(Screen.RadioParadise.route) },
                        onNavigateToRadioBrowser = { navController.navigate(Screen.RadioBrowser.route) },
                    ),
                    onNavigateToSettings = onNavigateToSettings,
                )
            }
            composable(Screen.SomaFm.route) {
                SomaFmRoute(
                    deviceState = deviceState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.RadioParadise.route) {
                RadioParadiseRoute(
                    deviceState = deviceState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable(Screen.RadioBrowser.route) {
                RadioBrowserRoute(
                    deviceState = deviceState,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
        composable(Screen.Settings.route) {
            SettingsRoute(
                deviceState = deviceState,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}