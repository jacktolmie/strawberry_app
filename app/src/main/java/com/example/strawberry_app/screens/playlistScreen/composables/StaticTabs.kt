package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks

@Composable
fun StaticTabs(
    callbacks: PlaylistCallbacks,
    isPortrait: Boolean,
    playlists: List<PlaylistEntity>,
    selectedTabIndex: Int,
    onTabSelected: (Int, Long) -> Unit,
    modifier: Modifier = Modifier
){
    SecondaryTabRow(
        modifier = modifier
            .fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        divider = {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant,
                thickness = 2.dp
            )
        }

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
fun StaticTabPreview(){
    StaticTabs(
        callbacks = PlaylistCallbacks(),
        isPortrait = true,
        playlists = samplePlaylists(),
        selectedTabIndex = 1,
        onTabSelected = {_,_ ->}
    )
}