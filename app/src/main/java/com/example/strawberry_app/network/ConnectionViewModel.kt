package com.example.strawberry_app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.network.protocol.IncomingMessage
import com.example.strawberry_app.server.ServerInfo
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val networkManager: NetworkManager,
    private val serverRepository: ServerRepository
) : ViewModel()
{
    val connectionState = networkManager.connectionStateFlow

    private val _incomingMessages = MutableSharedFlow<IncomingMessage>(replay = 0, extraBufferCapacity = 64)
    val incomingMessage = _incomingMessages.asSharedFlow()

    private var latestServerInfo: ServerInfo? = null

    init {
        serverRepository.serverInfoFlow
            .distinctUntilChanged()
            .onEach {
                android.util.Log.d("ConnectionViewModel", "Server info changed. Reconnecting...")
                if (networkManager.connectionStateFlow.value !is ConnectionState.Connected) {
                    reconnect()
                }
            }
            .launchIn(viewModelScope)
    }

    fun connect() {
        viewModelScope.launch {
            val info = serverRepository.serverInfoFlow.first()
            networkManager.connect( info)
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            networkManager.disconnect()
        }
    }

    fun reconnect() {
        viewModelScope.launch {
            val info = serverRepository.serverInfoFlow.first()
            networkManager.disconnect()
            networkManager.connect(info)
        }
    }

    fun sendCommand(command: String) {
        viewModelScope.launch {
            networkManager.sendCommand("""{"command":"$command"}""") //Replace with enums???
        }
    }
}