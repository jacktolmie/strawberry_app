package com.example.strawberry_app

import android.app.Application
import com.example.strawberry_app.screens.repositories.MessageRepository
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject

@HiltAndroidApp
class Main : Application(){
    @Inject
    lateinit var messageRepository: MessageRepository
}