package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.dao.SongWithPosition

@Composable
fun CurrentPlaylist(
    playlist: List<SongWithPosition>,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)

    ) {
        items(playlist){ song ->
            SongItem(song)
        }
    }
}