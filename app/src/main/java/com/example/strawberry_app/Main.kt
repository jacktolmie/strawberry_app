package com.example.strawberry_app

import android.app.Application
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.server.ServerInfo
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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