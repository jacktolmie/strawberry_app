package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData

@Composable
fun ScrollableTabs(
    callbacks: PlaylistCallbacks,
    playlistsData: PlaylistsData,
    playlistId: Long,
    selectedTabIndex: Int,
    onTabSelected: (Int, Long) -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
){
    SecondaryScrollableTabRow( modifier = modifier
        .fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        scrollState = scrollState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        divider = {},
    ) {
        MakeTabs(
            callbacks = callbacks,
            playlistsData = playlistsData,
            playlistId = playlistId,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )
    }
}