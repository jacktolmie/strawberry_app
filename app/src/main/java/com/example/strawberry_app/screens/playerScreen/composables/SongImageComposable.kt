package com.example.strawberry_app.screens.playerScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.playerScreen.PlayerCallbacks
import com.example.strawberry_app.screens.playerScreen.PlayerScreenState

@Composable
fun SongImageComposable(
    callbacks: PlayerCallbacks,
    playerScreenValues: PlayerScreenState
){
    Image(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(.75f)
        .aspectRatio(1f)
        .padding(10.dp),
        // Find the current image url or default strawberry image
        painter = painterResource(
            R.drawable.strawberry),
        contentDescription = "Test Image",
        contentScale = ContentScale.Crop
    )
}