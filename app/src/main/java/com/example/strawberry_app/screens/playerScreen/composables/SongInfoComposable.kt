package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.ServerValues
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun SongInfoComposable(
    playerScreenValues: PlayerScreenState,
    modifier: Modifier = Modifier
){
    // Song text for the song playing
    Text(
        text = playerScreenValues.playerValues.currentSong.title,
        modifier = modifier.basicMarquee(
            iterations = Int.MAX_VALUE,
            repeatDelayMillis = 10000,
            spacing = MarqueeSpacing.fractionOfContainer(0.1f)
        ),
        maxLines = 1
    )

    val artistAlbum = listOfNotNull(
        playerScreenValues.playerValues.currentSong.artist,
        playerScreenValues.playerValues.currentSong.album
    ).joinToString(if(playerScreenValues.playerValues.currentSong.artist.isNotBlank()) " • " else "")

    // Album/Artist text.
    Text(
        text = artistAlbum,
        modifier = modifier.basicMarquee(
            iterations = Int.MAX_VALUE,
            repeatDelayMillis = 10000,
            spacing = MarqueeSpacing.fractionOfContainer(0.1f)
        ),
        maxLines = 1
    )
}

@Preview
@Composable
fun SongInfoPreview(){
    SongInfoComposable(
        PlayerScreenState(ServerValues()),
        Modifier.background(Color.White)
    )
}