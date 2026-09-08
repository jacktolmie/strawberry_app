package com.example.strawberry_app.screens.radioScreen.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.screens.devices.DeviceTypes
import com.example.strawberry_app.screens.radioScreen.RadioCallbacks
import com.example.strawberry_app.screens.radioScreen.RadioScreenState

@Composable
fun RadioPortraitScreen(
    callbacks: RadioCallbacks,
    radioScreenState: RadioScreenState,
    deviceTypes: DeviceTypes, // for bottom padding
    modifier: Modifier = Modifier
){
    Column(modifier = modifier
        .fillMaxSize()
        .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ) {

    }
}

@Preview
@Composable
fun RadioPortraitPreview(){
    RadioPortraitScreen(
        callbacks = RadioCallbacks(),
        radioScreenState = RadioScreenState(),
        deviceTypes = DeviceTypes.PHONE
    )
}
