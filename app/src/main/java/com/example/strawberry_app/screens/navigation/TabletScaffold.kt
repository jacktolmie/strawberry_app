package com.example.strawberry_app.screens.navigation

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.radioScreen.RadioRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute

@Composable
fun TabletScaffold(
    deviceType: DeviceTypesBreakdown,
    isPortrait: Boolean,
    isRow: Boolean,
    selectedIndex: Int,
    modifier: Modifier
){
    PlayerRoute(
        modifier = modifier,
        isPortrait = isPortrait,
        deviceType = deviceType
    )

    if ( isRow){
        VerticalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
    } else {
        HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.onSurface)
    }

    when (selectedIndex) {
        2 -> RadioRoute(
            modifier = modifier,
            isPortrait = isPortrait,
            deviceType = deviceType
        )
        3 -> SettingsRoute(
            modifier = modifier,
            isPortrait = isPortrait,
            deviceType = deviceType
        )
        else -> PlaylistRoute(
            modifier = modifier,
            isPortrait = isPortrait,
            deviceType = deviceType
        )
    }
}