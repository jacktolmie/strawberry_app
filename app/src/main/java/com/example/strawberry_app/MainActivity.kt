package com.example.strawberry_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.window.core.layout.WindowSizeClass
import com.example.strawberry_app.ui.theme.Strawberry_appTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Strawberry_appTheme {
                val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
                MyApp( windowSizeClass = windowSizeClass )
            }
        }
    }
}