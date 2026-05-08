package com.example.strawberry_app.network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val networkManager:     NetworkManager,
    private val serverRepository: ServerRepository
) : ViewModel()
{
    val connectionState = networkManager.connectionStateFlow

//    private val _incomingMessages = MutableSharedFlow<IncomingMessage>(replay = 0, extraBufferCapacity = 64)
//    val incomingMessage = _incomingMessages.asSharedFlow()

    init {
        viewModelScope.launch {
            serverRepository.serverInfoFlow
                .debounce(300)
                .distinctUntilChanged()
                .collect { info ->

                    if (info.ip.isBlank()) return@collect

                    Log.d("ConnectionVM", "Server changed → reconnecting")

                    Log.d("ConnectionVM", "FLOW EMITTED: $info")

                    networkManager.disconnect()

                    Log.d("ConnectionVM", "DISCONNECT CALLED")

                    networkManager.connect(info)

                    Log.d("ConnectionVM", "CONNECT CALLED")
                }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            networkManager.disconnect()
        }
    }

    fun sendCommand(command: String) {
        viewModelScope.launch {
            networkManager.sendCommand("""{"command":"$command"}""") //Replace with enums???
        }
    }
}