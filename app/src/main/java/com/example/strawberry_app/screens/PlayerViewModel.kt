package com.example.strawberry_app.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val networkManager: NetworkManager
): ViewModel(){

    private val _connectionState = networkManager.connectionStateFlow

    fun isConnected() = _connectionState.value == ConnectionState.Connected
//    fun getVolume() = sendCommand(OutgoingMessage.)

    fun sendCommand(command: OutgoingMessage) {
        viewModelScope.launch {
            networkManager.sendCommand(command)
        }
    }

}