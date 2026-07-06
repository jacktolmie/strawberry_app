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
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.music.Playlist
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.screens.formatTime
import com.example.strawberry_app.screens.playlistScreen.PlaylistState

@Composable
fun SongItem(
    song: SongWithPosition
){
    Column( modifier = Modifier
        .background(Color.White)
        .fillMaxWidth()
        .padding(2.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Row for song title and length
        Row(modifier = Modifier
            .padding(start = 5.dp)

        ) {
            SongText(
                text = song.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1F)
            )
            SongText(
                text = formatTime(song.length),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // Row for artist and album name
        Row(modifier = Modifier
            .padding(start = 5.dp)
        ){
            SongText(
                text = song.artist,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1F) )
            SongText(
                text = song.album,
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
    SongInfo(
        id = 1,
        url = "Some url",
        artist = "The Amazing Band",
        album = "The best of Amazing Band",
        coverImage = "Some cover",
        length = 1000L,
        title = "Bird Madness"
    )
}