package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.playlistScreen.PlaylistState

@Composable
fun MedLrgScreenTabs(
    modifier: Modifier = Modifier,
    playlistState: PlaylistState
){
    val selectedTabIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    SecondaryScrollableTabRow( modifier = Modifier
        .fillMaxSize(),
        selectedTabIndex = selectedTabIndex,
        scrollState = scrollState,
        divider = {
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    ) {

    }
}

@Preview
@Composable
fun MedLrgPreview(){
    MedLrgScreenTabs(
        Modifier.background(Color.White),
        PlaylistState(
            currentPlaylist = 1,
            activePlaylist = 1,
            currentSongIndex = 1,
            currentSongData = SongWithPosition(
                    id = 1,
                    url = "Some url",
                    artist = "The Amazing Band",
                    album = "The best of Amazing Band",
                    coverImage = "Some cover",
                    length = 1000L,
                    position = 1,
                    title = "Bird Madness"
            )
        )
    )

}