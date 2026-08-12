package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks

@Composable
fun ScrollableTabs(
    callbacks: PlaylistCallbacks,
    isPortrait: Boolean,
    onTabSelected: (Int, Long) -> Unit,
    playlists: List<PlaylistEntity>,
    scrollState: ScrollState,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier
){
    SecondaryScrollableTabRow( modifier = modifier.fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        scrollState = scrollState,
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
        isPortrait = true,
        playlists = samplePlaylists(),
        selectedTabIndex = 0,
        onTabSelected = {_,_ -> },
        scrollState = scrollState,
        modifier = Modifier
    )
}