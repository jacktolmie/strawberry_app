package com.example.strawberry_app

import android.app.Application
import com.example.strawberry_app.screens.MessageRepository
import com.example.strawberry_app.screens.playlistScreen.PlaylistRepository
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject

@HiltAndroidApp
class Main : Application(){
    @Inject
    lateinit var messageRepository: MessageRepository
}