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
import com.example.strawberry_app.screens.classes.DeviceState
import com.example.strawberry_app.screens.playerScreen.PlayerRoute

@Composable
fun TabletScaffold(
    deviceState: DeviceState,
    navController: NavHostController,
    onNavigateToSettings: () -> Unit,
) {
    if (deviceState.isPortrait) {
        Column(modifier = Modifier.fillMaxSize()) {
            PlayerRoute(
                deviceState = deviceState,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings,
            )
            HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
            AppNavHost(
                deviceState = deviceState,
                navController = navController,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings
            )
        }
    } else {
        Row(modifier = Modifier.fillMaxSize()) {
            PlayerRoute(
                deviceState = deviceState,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings
            )
            VerticalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
            AppNavHost(
                deviceState = deviceState,
                navController = navController,
                modifier = Modifier.weight(1f),
                onNavigateToSettings = onNavigateToSettings,
            )
        }
    }
}