package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.devices.DeviceTypesBreakdown
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks

@Composable
fun ScrollableTabs(
    callbacks: PlaylistCallbacks,
    isPortrait: Boolean,
    tabScreenState: TabScreenState,
    modifier: Modifier = Modifier
){
    SecondaryScrollableTabRow( modifier = modifier.fillMaxWidth(),
        selectedTabIndex = tabScreenState.selectedTabIndex,
        scrollState = tabScreenState.scrollState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        divider = {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 2.dp
            )
        },
    ) {
        MakeTabs(
            callbacks = callbacks,
            isPortrait = isPortrait,
            playlists = tabScreenState.playlists,
            selectedTabIndex = tabScreenState.selectedTabIndex,
            onTabSelected = tabScreenState.onTabSelected
        )
    }
}

@Preview
@Composable
fun ScrollablePreview(){
    val tabScreenState = TabScreenState(
        onTabSelected = {_,_->},
        playlists = samplePlaylists(),
        scrollState = ScrollState(0),
        selectedTabIndex = 0,
        deviceType = DeviceTypesBreakdown.PHONE_PORTRAIT
    )
    ScrollableTabs(
        callbacks = PlaylistCallbacks(),
        isPortrait = true,
        tabScreenState = tabScreenState,
        modifier = Modifier
    )
}