package com.example.strawberry_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.strawberry_app.network.ConnectionViewModel
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.screens.Player
import com.example.strawberry_app.screens.SettingsScreen
import com.example.strawberry_app.server.ServerInfo
import com.example.strawberry_app.ui.theme.Strawberry_appTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val connectionViewModel: ConnectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Strawberry_appTheme {
                SettingsScreen()
//                Main()
                connectionViewModel
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Strawberry_appTheme {
        Greeting("Android")
    }
}