package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.screens.playlistScreen.PlaylistCallbacks
import com.example.strawberry_app.ui.theme.icons.favorite

@Composable
fun MakeTabs(
    callbacks: PlaylistCallbacks,
    isPortrait: Boolean,
    playlists: List<PlaylistEntity>,
    selectedTabIndex: Int,
    onTabSelected: (Int, Long) -> Unit,
    modifier: Modifier = Modifier
){
    playlists.forEachIndexed { index, entity ->
        Tab(
            modifier = modifier,
            selected = selectedTabIndex == index,
            onClick = {
                onTabSelected(index, entity.id)
                callbacks.onPlaylistSelected(entity.id)
            },
            text = {
                if (!isPortrait && entity.favourite) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = entity.name,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = favorite,
                            contentDescription = stringResource(R.string.playlist_favourite),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                } else {
                    Text(
                        text = entity.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = if (isPortrait) MaterialTheme.typography.bodyMedium
                        else MaterialTheme.typography.bodySmall
                    )
                }
            },
            icon = {
                if (isPortrait && entity.favourite) {
                    Icon(
                        imageVector = favorite,
                        contentDescription = stringResource(R.string.playlist_favourite),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )
    }
}