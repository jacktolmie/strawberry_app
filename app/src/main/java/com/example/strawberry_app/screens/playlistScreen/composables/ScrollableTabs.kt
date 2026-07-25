package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks

@Composable
fun ScrollableTabs(
    callbacks: PlaylistCallbacks,
    playlists: List<PlaylistEntity>,
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
            playlists = playlists,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = onTabSelected
        )
    }
}

@Preview
@Composable
fun ScrollablePreview(){
    val scrollState = rememberScrollState()
    ScrollableTabs(
        callbacks = PlaylistCallbacks(),
        playlists = samplePlaylists(),
        selectedTabIndex = 0,
        onTabSelected = {_,_ -> },
        scrollState = scrollState,
        modifier = Modifier
    )
}