package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.classes.ServerGuiValues
import com.example.strawberry_app.screens.composables.TextBox
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun SongInfoComposable(
    playerScreenValues: PlayerScreenState
){
    // Song text for the song playing
    TextBox(
        color = MaterialTheme.colorScheme.onSurface,
        text = playerScreenValues.playerValues.currentSong.title,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .basicMarquee(
                iterations = Int.MAX_VALUE,
                repeatDelayMillis = 10000,
                spacing = MarqueeSpacing.fractionOfContainer(0.1f)
            )
    )

    val artistAlbum = listOfNotNull(
        playerScreenValues.playerValues.currentSong.artist,
        playerScreenValues.playerValues.currentSong.album
    ).joinToString(if(playerScreenValues.playerValues.currentSong.artist.isNotBlank()) " • " else "")

    // Album/Artist text.
    TextBox(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textStyle = MaterialTheme.typography.bodyMedium,
        text = artistAlbum,
        modifier = Modifier
            .basicMarquee(
            iterations = Int.MAX_VALUE,
            repeatDelayMillis = 10000,
            spacing = MarqueeSpacing.fractionOfContainer(0.1f)
        )
    )
}

@Preview
@Composable
fun SongInfoPreview(){
    SongInfoComposable(
        PlayerScreenState(ServerGuiValues())
    )
}