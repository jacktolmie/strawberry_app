package com.example.strawberry_app.screens.radioScreen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.screens.composables.TopBar
import com.example.strawberry_app.screens.devices.DeviceTypes
import com.example.strawberry_app.screens.radioScreen.RadioCallbacks
import com.example.strawberry_app.screens.radioScreen.RadioScreenState
import com.example.strawberry_app.ui.theme.icons.radio

@Composable
fun RadioPortraitScreen(
    callbacks: RadioCallbacks,
    radioScreenState: RadioScreenState,
    deviceTypes: DeviceTypes, // for bottom padding
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            TopBar(
                heading = R.string.radio_main_title,
                onClick = { onNavigateToSettings()},
                showMoreOptions = true
            )
            Icon(imageVector = radio, contentDescription = stringResource(R.string.navbar_radio))
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues)
            .fillMaxSize()
            .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {

        }
    }

}

@Preview
@Composable
fun RadioPortraitPreview(){
    RadioPortraitScreen(
        callbacks = RadioCallbacks(),
        radioScreenState = RadioScreenState(),
        deviceTypes = DeviceTypes.PHONE,
        onNavigateToSettings = {}
    )
}
