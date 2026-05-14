package com.example.strawberry_app.network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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


//    val connectionUiState = when (val state = connectionState.value) {
//
//        ConnectionState.Connected ->
//            "Connected"
//
//        ConnectionState.Connecting ->
//            "Connecting"
//
//        ConnectionState.Disconnected ->
//            "Disconnected"
//
//        is ConnectionState.Error ->
//            "Error: ${state.message}"
//
//        is ConnectionState.Reconnecting ->
//            "Reconnecting in ${connectionState.time}\nAttempt: ${connectionState.attempt}"
//    }