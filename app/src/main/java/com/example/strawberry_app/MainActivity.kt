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
import com.example.strawberry_app.music.PlaylistRepository
import com.example.strawberry_app.network.ConnectionViewModel
import com.example.strawberry_app.ui.theme.Strawberry_appTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val connectionViewModel: ConnectionViewModel by viewModels()

    // Testing music repository. Delete when done.
    @Inject
    lateinit var playlistRepository: PlaylistRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Strawberry_appTheme {
                connectionViewModel
                Main()
                Nav()
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