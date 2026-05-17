package com.example.strawberry_app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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
                .distinctUntilChanged()
                .collect { info ->
                    if (info?.ip?.isBlank() == true) return@collect
                }
        }
    }

    fun manualDisconnect(disconnectPressed: Boolean) {
        viewModelScope.launch {
            networkManager.manualDisconnect(disconnectPressed)
        }
    }

    fun sendCommand(command: String) {
        viewModelScope.launch {
            networkManager.sendCommand("""{"command":"$command"}""") //Replace with enums???
        }
    }
}