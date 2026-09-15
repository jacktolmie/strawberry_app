package com.example.strawberry_app.screens.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playerScreen.PlayerRoute

@Composable
fun TabletScaffold(
    navController: NavHostController,
    deviceType: DeviceTypesBreakdown,
    onNavigateToSettings: () -> Unit,
    isPortrait: Boolean,
    isTablet: Boolean,
    showTopBar: Boolean
) {
    if (isPortrait) {
        Column(modifier = Modifier.fillMaxSize()) {
            PlayerRoute(
                isPortrait = isPortrait,
                deviceType = deviceType,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings,
                showTopBar = showTopBar
            )
            HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
            AppNavHost(
                navController = navController,
                deviceType = deviceType,
                isPortrait = isPortrait,
                isTablet = isTablet,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings,
                showTopBar = showTopBar
            )
        }
    } else {
        Row(modifier = Modifier.fillMaxSize()) {
            PlayerRoute(
                isPortrait = isPortrait,
                deviceType = deviceType,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings,
                showTopBar = showTopBar
            )
            VerticalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
            AppNavHost(
                navController = navController,
                deviceType = deviceType,
                isPortrait = isPortrait,
                isTablet = isTablet,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings,
                showTopBar = showTopBar
            )
        }
    }
}