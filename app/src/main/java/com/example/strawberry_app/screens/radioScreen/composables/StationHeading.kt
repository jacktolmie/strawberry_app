package com.example.strawberry_app.screens.radioScreen.composables

import androidx.compose.foundation.background
import com.example.strawberry_app.R
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.ModifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.screens.composables.TextBox
import com.example.strawberry_app.screens.radioScreen.RadioCallbacks
import com.example.strawberry_app.screens.radioScreen.RadioScreenState

@Composable
fun StationHeading(
    callbacks: RadioCallbacks,
    radioScreenState: RadioScreenState,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.surfaceContainerLow)


    ) {
        TextBox(
            color = MaterialTheme.colorScheme.onSurface,
            text = radioScreenState.stationSource.first().ifEmpty { stringResource(R.string.songInfo_no_song_playing) },
            textStyle = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center

        )
    }
}

@Preview
@Composable
fun StationHeadingPreview(){
    StationHeading(
        callbacks = RadioCallbacks(),
        radioScreenState = previewRadioScreenState
    )
}