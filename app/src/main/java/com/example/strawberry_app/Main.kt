package com.example.strawberry_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Main: Application() {
//    @Inject
//    lateinit var serverRepository: ServerRepository
//    val serverInfo = ServerInfo()
//    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onCreate() {
        super.onCreate()
    }

}