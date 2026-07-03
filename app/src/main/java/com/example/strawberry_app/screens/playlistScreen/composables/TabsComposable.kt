package com.example.strawberry_app.screens.playlistScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MedLrgScreenTabs(
    modifier: Modifier = Modifier
){
    val selectedTabIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

        SecondaryScrollableTabRow( modifier = Modifier
            .fillMaxSize(),
            selectedTabIndex = selectedTabIndex,
            scrollState = scrollState,
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