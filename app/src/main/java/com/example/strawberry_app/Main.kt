package com.example.strawberry_app

import android.app.Application
import com.example.strawberry_app.network.NetworkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Main: Application() {
    @Inject lateinit var networkManager: NetworkManager

    override fun onCreate() {
        super.onCreate()

        networkManager.start()
    }
}