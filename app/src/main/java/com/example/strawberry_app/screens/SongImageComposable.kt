package com.example.strawberry_app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import java.io.File

@Composable
fun SongImageComposable(
    imageArt: File? = null,
    crossfade: Boolean,
    modifier: Modifier // Force them to pass a modifier.
){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageArt?: R.drawable.strawberry)
            .crossfade(crossfade) //(true)
            .build(),
        placeholder = painterResource(R.drawable.strawberry),
        fallback = painterResource(R.drawable.strawberry),
        error = painterResource(R.drawable.strawberry),
        contentDescription = stringResource(R.string.player_album_art),
        contentScale = ContentScale.Crop,
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    )
}

@Preview
@Composable
fun SongImagePreview(){
    SongImageComposable(
        crossfade = true,
        modifier = Modifier.fillMaxHeight()
            .fillMaxWidth(.75f)
            .aspectRatio(1f)
            .padding(10.dp)
    )
}