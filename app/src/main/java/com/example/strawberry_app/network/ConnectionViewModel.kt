package com.example.strawberry_app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.network.protocol.OutgoingMessage
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val networkManager:     NetworkManager,
    private val serverRepository: ServerRepository
) : ViewModel()
{
    val connectionState = networkManager.connectionStateFlow

    init {
        viewModelScope.launch {
            serverRepository.serverInfoFlow
                .debounce(300)
                .collect { info ->
                    if (info != null) networkManager.connect(info)
                    else networkManager.disconnect()
                }
        }
    }

    fun manualDisconnect(disconnectPressed: Boolean) {
        viewModelScope.launch {
            networkManager.manualDisconnect(disconnectPressed)
        }
    }


    fun sendCommand(command: OutgoingMessage) {
        viewModelScope.launch {
            networkManager.sendCommand(command)
        }
    }
}