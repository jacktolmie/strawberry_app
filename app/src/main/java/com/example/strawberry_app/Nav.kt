package com.example.strawberry_app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.strawberry_app.screens.ScreenList
import com.example.strawberry_app.screens.playerScreen.PlayerRoute
import com.example.strawberry_app.screens.playlistScreen.PlaylistRoute
import com.example.strawberry_app.screens.settingsScreen.SettingsRoute

@Composable
fun Nav(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenList.Playlist.route // ScreenList.Settings.route
    ){
        composable(ScreenList.Player.route){
            PlayerRoute()
        }

        composable(ScreenList.Playlist.route){
            PlaylistRoute()
        }

        composable(ScreenList.Settings.route){
            SettingsRoute()
        }
    }

}