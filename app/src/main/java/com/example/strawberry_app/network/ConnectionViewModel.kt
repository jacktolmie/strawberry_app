package com.example.strawberry_app.network

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsRouteData(
    val connectBtnText: String = "Disconnected",
    val connectionColour: Color = Color.Red,
    val connectionState: String = "Disconnected"
)

@OptIn(FlowPreview::class)
@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val networkManager:     NetworkManager,
    private val serverRepository: ServerRepository
) : ViewModel()
{
    val connectionState = networkManager.connectionStateFlow

    val routeData: StateFlow<SettingsRouteData> = connectionState
        .map{ state ->
            SettingsRouteData(
                connectBtnText = if (state == ConnectionState.Connected)
                    "Disconnect" else "Connect",
                connectionColour = when (state) {
                    is ConnectionState.Connected    -> Color.Green
                    is ConnectionState.Connecting   -> Color.Yellow
                    is ConnectionState.Disconnected -> Color.Red
                    is ConnectionState.Error        -> Color.Red
                    is ConnectionState.Reconnecting -> Color.Yellow
                },
                connectionState = when (val s = state) {
                    ConnectionState.Connected    -> "Connected"
                    ConnectionState.Connecting   -> "Connecting"
                    ConnectionState.Disconnected -> "Disconnected"
                    is ConnectionState.Error        -> s.message
                    is ConnectionState.Reconnecting ->
                        "Reconnecting in ${s.time}\nAttempt: ${s.attempt}"
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsRouteData()
        )

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
}