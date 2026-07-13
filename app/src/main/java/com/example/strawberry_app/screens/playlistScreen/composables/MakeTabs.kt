package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData
import com.example.strawberry_app.ui.theme.icons.favorite

@Composable
fun MakeTabs(
    callbacks: PlaylistCallbacks,
    playlistsData: PlaylistsData,
    playlistId: Long,
    selectedTabIndex: Int,
    onTabSelected: (Int, Long) -> Unit,
    modifier: Modifier = Modifier
){
    playlistsData.playlists.forEachIndexed { index, entity ->
        Tab(
            modifier = modifier,
            text = { Text(entity.name, color = MaterialTheme.colorScheme.onSurface) },
            selected = selectedTabIndex == index,
            onClick = {
                onTabSelected(index, entity.id)
                callbacks.onPlaylistSelected(entity.id)
            },
            icon = {
                if(entity.favourite) {
                    Icon(
                        imageVector = favorite,
                        contentDescription = "Favourite",
                        tint = MaterialTheme.colorScheme.onSurface)
                }
            }
        )
    }
}