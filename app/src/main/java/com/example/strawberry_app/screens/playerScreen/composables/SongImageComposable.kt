package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.ServerGuiValues
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun SongImageComposable(
    playerScreenValues: PlayerScreenState,
    modifier: Modifier = Modifier
){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(playerScreenValues.albumArtFile)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.strawberry),
        fallback = painterResource(R.drawable.strawberry),
        error = painterResource(R.drawable.strawberry),
        contentDescription = stringResource(R.string.player_album_art),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(.75f)
            .aspectRatio(1f)
            .padding(10.dp)
    )
}

@Preview
@Composable
fun SongImagePreview(){
    SongImageComposable(
        PlayerScreenState(ServerGuiValues())
    )
}