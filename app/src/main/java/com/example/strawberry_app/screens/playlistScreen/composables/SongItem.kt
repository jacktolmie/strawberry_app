package com.example.strawberry_app.screens.playlistScreen.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.screens.SongImageComposable
import com.example.strawberry_app.screens.SongText
import com.example.strawberry_app.screens.formatTime
import java.io.File
import com.example.strawberry_app.ui.theme.icons.more_horiz

@Composable
fun SongItem(
    song: SongWithPosition,
    modifier: Modifier = Modifier,
    imageArt: File? = null

) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        SongImageComposable(
            imageArt = imageArt,
            crossfade = false,
            modifier = Modifier
                .size(48.dp) // Absolute size anchor for a clean row
                .clip(RoundedCornerShape(4.dp))
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Column(
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SongText(
                    text = song.title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold
                )
                SongText(
                    text = formatTime(song.length),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text = listOf(song.artist, song.album)
                    .filter { it.isNotEmpty() }
                    .joinToString(separator = " • ")

                SongText(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )

                Icon(imageVector = more_horiz, contentDescription = "")

                // We leave the right side blank here so it aligns perfectly
                // under the clean layout, or you could place an explicit 'IconButton' here later.
            }
        }

    }
//    }
}

@Preview
@Composable
fun SongItemPreview() {
    SongItem(
        song = SongWithPosition(
            id = 1,
            url = "Some url",
            artist = "The Amazing Band",
            album = "The best of Amazing Band",
            coverImage = "Some cover",
            length = 1000L,
            title = "Bird Madness",
            position = 1L
        ),
        modifier = Modifier
            .size(48.dp) // Absolute size anchor for a clean row
            .clip(RoundedCornerShape(4.dp))
    )
}

/*
@Composable
fun SongItem(
    song: SongWithPosition,
    modifier: Modifier = Modifier
){
    Column( modifier = modifier
        .background(MaterialTheme.colorScheme.surface)
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Row for song title and length
        Row(modifier = Modifier
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
        ){
            SongText(
                text = song.artist,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1F)
                )
            SongText(
                text = song.album,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
*/