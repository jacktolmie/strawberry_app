package com.example.strawberry_app.screens.playlistScreen.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.formatTime
import com.example.strawberry_app.screens.playlistScreen.PlaylistState

@Composable
fun SongItem(
    playlistState: PlaylistState
){
    Column( modifier = Modifier
        .background(Color.White)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Row for song title and length
        Row(modifier = Modifier
            .padding(start = 5.dp)

        ) {
            SongText(
                text = playlistState.currentSongData?.title ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1F)
            )
            SongText(
                text = formatTime(playlistState.currentSongData?.length ?: 1000),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Row for artist and album name
        Row(modifier = Modifier
            .padding(start = 5.dp)
        ){
            SongText(
                text = playlistState.currentSongData?.artist ?: "Artist name",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1F) )
            SongText(
                text = playlistState.currentSongData?.album ?: "Album name",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SongText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier
){
    Text(
        modifier = modifier,
        text = text,
        overflow = TextOverflow.Ellipsis,
        style = style,
        maxLines = 1
    )
}

@Preview
@Composable
fun SongItemPreview(){
    SongItem(
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