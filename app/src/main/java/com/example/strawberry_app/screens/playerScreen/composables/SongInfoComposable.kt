package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun SongInfoComposable(
    playerScreenValues: PlayerScreenState
){
    // Song text for the song playing
    Text(
        text = playerScreenValues.playerValues.currentSong.title,
        modifier = Modifier.basicMarquee(
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
        modifier = Modifier.basicMarquee(
            iterations = Int.MAX_VALUE,
            repeatDelayMillis = 10000,
            spacing = MarqueeSpacing.fractionOfContainer(0.1f)
        ),
        maxLines = 1
    )
}