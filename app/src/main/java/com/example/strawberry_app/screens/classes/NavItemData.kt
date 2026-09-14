package com.example.strawberry_app.screens.classes

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItemData(
    val iconVector: ImageVector,
    val labelRes: Int,
    val screen: Screen
)