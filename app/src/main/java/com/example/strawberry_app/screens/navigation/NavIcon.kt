package com.example.strawberry_app.screens.navigation

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.strawberry_app.R
import com.example.strawberry_app.ui.theme.icons.music_note

@Composable
fun NavIcon(image: ImageVector, description: Int){
    Icon(
        imageVector = image,
        contentDescription = stringResource(description)
    )
}

@Preview
@Composable
fun IconPreview() {
    NavIcon(
        image = music_note,
        description = R.string.navbar_player
    )
}