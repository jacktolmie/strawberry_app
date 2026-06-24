package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MedLrgScreenTabs(
    modifier: Modifier = Modifier
){
        SecondaryScrollableTabRow(
//        selectedTabIndex = playlistRepository.playlistState.collectAsState()
    ) {

    }
}

@Preview
@Composable
fun MedLrgPreview(){
    MedLrgScreenTabs(
        Modifier.background(Color.White)
    )
}