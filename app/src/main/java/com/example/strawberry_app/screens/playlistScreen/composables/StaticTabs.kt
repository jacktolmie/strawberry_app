package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData

@Composable
fun StaticTabs(
    callbacks: PlaylistCallbacks,
    playlistsData: PlaylistsData,
    playlistId: Long,
    selectedTabIndex: Int,
    onTabSelected: (Int, Long) -> Unit,
    modifier: Modifier = Modifier
){
    SecondaryTabRow(
        modifier = Modifier
            .fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        divider = {}

    ) {
        MakeTabs(
            callbacks = callbacks,
            playlistsData = playlistsData,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected,
            playlistId = playlistId
        )
    }
}