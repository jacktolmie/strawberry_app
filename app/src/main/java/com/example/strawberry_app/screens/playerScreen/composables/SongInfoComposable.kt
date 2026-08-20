package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.classes.ServerGuiValues
import com.example.strawberry_app.screens.composables.TextBox
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun SongInfoComposable(
    playerScreenValues: PlayerScreenState
){
    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        // Song text for the song playing
        TextBox(
            color = MaterialTheme.colorScheme.onSurface,
            text = playerScreenValues.playerValues.currentSong.title.ifEmpty { "No Song Playing" },
            textStyle = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
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
        ).joinToString(
            if(playerScreenValues.playerValues.currentSong.artist.isNotBlank()) " • "
            else {
                "${playerScreenValues.playerValues.totalSongs} Songs • " +
                "${playerScreenValues.playerValues.totalArtists} Artists  • " +
                 "${playerScreenValues.playerValues.totalAlbums} Albums"
            }
        )

        // Album/Artist text.
        TextBox(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textStyle = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            text = artistAlbum,
            modifier = Modifier
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 10000,
                    spacing = MarqueeSpacing.fractionOfContainer(0.1f)
                )
        )
    }
}

@Preview
@Composable
fun SongInfoPreview(){
    SongInfoComposable(
        PlayerScreenState(ServerGuiValues())
    )
}