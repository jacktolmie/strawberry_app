package com.example.strawberry_app.screens.radioScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.composables.SongImageComposable
import com.example.strawberry_app.screens.composables.TextBox
import com.example.strawberry_app.screens.radioScreen.RadioCallbacks
import com.example.strawberry_app.screens.radioScreen.RadioScreenState
import com.example.strawberry_app.screens.radioScreen.previewRadioScreenState
import com.example.strawberry_app.ui.theme.icons.radio
import com.example.strawberry_app.ui.theme.icons.read_more


@Composable
fun StationSourceScreen(
    callbacks: RadioCallbacks,
    radioScreenState: RadioScreenState,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically

        ){
            TextBox(modifier = Modifier.padding(5.dp),
                color = MaterialTheme.colorScheme.onSurface,
                textRes = R.string.radio_main_title,
                textStyle = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = radio,
                contentDescription = stringResource(R.string.radio_radio_description)
            )
        }

        HorizontalDivider(
            Modifier.padding(bottom = 5.dp),
            thickness = 5.dp,
            color = MaterialTheme.colorScheme.onSurface
        )

        radioScreenState.stationNamesIcons.forEach {
            Row(modifier = Modifier
                .clickable(onClick = {}) // Fill in onClick
                .fillMaxWidth()
                .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.weight(.3F))
                SongImageComposable(
                    imageArt = it.icon,
                    crossfade = false,
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(4.dp))
                )
                Spacer(Modifier.weight(.3F))

                TextBox(modifier = Modifier.weight(1F),
                    color = MaterialTheme.colorScheme.onSurface,
                    text = it.station,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Left
                )
                Icon(
                    imageVector = read_more,
                    contentDescription = stringResource(R.string.radio_go_to_station)
                )
            }

        }

    }
}

@Preview
@Composable
fun StationSourceScreenPreview(){
    StationSourceScreen(
        callbacks = RadioCallbacks(),
        radioScreenState = previewRadioScreenState
    )
}