package com.example.strawberry_app.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.network.ConnectionState.Connected
import com.example.strawberry_app.network.ConnectionState.Connecting
import com.example.strawberry_app.network.ConnectionState.Disconnected
import com.example.strawberry_app.network.ConnectionState.Error
import com.example.strawberry_app.network.ConnectionState.Reconnecting
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ConnectionColour{
    GREEN, RED, YELLOW
}

data class SettingsGuiData(
    val connectBtnText: String = "Disconnected",
    val connectionColour: ConnectionColour = ConnectionColour.RED,
    val connectionState: String = "Disconnected"
)

@OptIn(FlowPreview::class)
@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val networkManager: NetworkManager,
) : ViewModel()
{
    val connectionState = networkManager.connectionStateFlow

    val routeData: StateFlow<SettingsGuiData> = connectionState
        .map{ state ->
            SettingsGuiData(
                connectBtnText = if (state == Connected)
                    "Disconnect" else "Connect",
                connectionColour = when (state) {
                    Connected       -> ConnectionColour.GREEN
                    Connecting      -> ConnectionColour.YELLOW
                    Disconnected    -> ConnectionColour.RED
                    is Error        -> ConnectionColour.RED
                    is Reconnecting -> ConnectionColour.YELLOW
                },
                connectionState = when (state) {
                    Connected       -> "Connected"
                    Connecting      -> "Connecting"
                    Disconnected    -> "Disconnected"
                    is Error        -> state.message
                    is Reconnecting ->
                        "Reconnecting in ${state.time}\nAttempt: ${state.attempt}"
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsGuiData()
        )

    fun manualDisconnect(disconnectPressed: Boolean) {
        viewModelScope.launch {
            networkManager.manualDisconnect(disconnectPressed)
        }
    }
}