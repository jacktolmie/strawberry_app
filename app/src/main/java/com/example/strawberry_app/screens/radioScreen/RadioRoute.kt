package com.example.strawberry_app.screens.radioScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

data class RadioCallbacks(
    val test: String = ""
)

@Composable
fun RadioRoute(
    radioViewModel: RadioViewModel
){
    val albumArtCollection by radioViewModel.albumArtCollection.collectAsStateWithLifecycle()
}