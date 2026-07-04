package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.screens.playlistScreen.PlaylistState
import com.example.strawberry_app.screens.playlistScreen.PlaylistsData
import com.example.strawberry_app.ui.theme.icons.favorite

@Composable
fun MedLrgScreenTabs(
    modifier: Modifier = Modifier,
    playlistsData: PlaylistsData
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    SecondaryScrollableTabRow( modifier = Modifier
        .fillMaxWidth(),
        selectedTabIndex = selectedTabIndex,
        scrollState = scrollState,
        divider = {
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }

    ) {playlistsData.playlistState
        playlistsData.playlists.forEachIndexed { index, entity ->
            Tab(modifier = Modifier
                    .background(Color.Black),
                text = { Text(entity.name, color = Color.White) },
                selected = selectedTabIndex == index,
                onClick = { },
                icon = {
                    if(entity.favourite) {
                        Icon(
                            imageVector = favorite,
                            contentDescription = "Favourite",
                            tint = Color.Yellow)
                    }
                }
            )
        }
    }
}

fun samplePlaylists() = listOf(
    PlaylistEntity(id = 1L, name = "Rock Classics", favourite = false),
    PlaylistEntity(id = 2L, name = "Jazz Favourites", favourite = true),
    PlaylistEntity(id = 3L, name = "Pop Hits", favourite = false)

)

fun sampleSongList() = listOf(
    SongInfo(
        artist = "Led Zeppelin",
        album = "Led Zeppelin IV",
        coverImage = "",
        id = 1L,
        length = 331000L,
        title = "Stairway to Heaven",
        url = "file:///music/stairway.mp3"
    ),
        SongInfo(
            artist = "Pink Floyd",
            album = "The Dark Side of the Moon",
            coverImage = "",
            id = 2L,
            length = 543000L,
            title = "Time",
            url = "file:///music/time.mp3"
        ),
        SongInfo(
            artist = "The Beatles",
            album = "Abbey Road",
            coverImage = "",
            id = 3L,
            length = 259000L,
            title = "Come Together",
            url = "file:///music/cometogether.mp3"
        )
    )


@Preview
@Composable
fun MedLrgPreview(){
    MedLrgScreenTabs(
        Modifier.background(Color.White),
        PlaylistsData(
            playlistState = PlaylistState(),
            samplePlaylists()
        )
    )
}