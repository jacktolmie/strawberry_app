package com.example.strawberry_app.server

import android.net.InetAddresses
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ServerViewModel @Inject constructor(
    private val serverRepository: ServerRepository
): ViewModel()
{
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    val serverInfo: StateFlow<ServerInfo?> =
        serverRepository.serverInfoFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    private var savedServerInfo: StateFlow<ServerInfo?> = serverInfo

    init {
        viewModelScope.launch {
            serverRepository.serverInfoFlow.collectLatest { info ->

                if(info != null && !_uiState.value.hasChanged) {
                    _uiState.update {
                        it.copy(
                            ip = info.ip,
                            port = info.port.toString(),
                            password = info.password
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            uiState
                .debounce(300)
                .collect {
                    validate()
                    showSaveButton()
                }
        }
    }

    fun cancel(){
        _uiState.update {
            it.copy(
                ip = savedServerInfo.value?.ip ?: "",
                port = savedServerInfo.value?.port.toString(),
                password = savedServerInfo.value?.password ?: "",
                ipError = null,
                portError = null,
                isIpValid = true,
                isPortValid = true,
                hasChanged = false,
            )
        }
    }

    private fun isValidIP(address: String): Boolean{
        return InetAddresses.isNumericAddress(address)
    }

    fun onIpChanged(newIp: String) {
        _uiState.update {
            it.copy(ip = newIp, hasChanged = true)
        }
    }

    fun onPasswordChanged(newPassword: String) = _uiState.update { it.copy(password = newPassword, hasChanged = true) }

    fun onPortChanged(newPort: String) {
        if (newPort.length <= 5 && newPort.all { it.isDigit() }) {
            _uiState.update { it.copy(port = newPort, hasChanged = true) }
        }
    }

    fun save() {
        _uiState.update { it.copy(hasChanged = false) }

        val state = _uiState.value

        if (!state.isPortValid) return

        val serverInfo = ServerInfo(
            ip = state.ip,
            port = state.port.toInt(),
            password = state.password
        )

        viewModelScope.launch {
            serverRepository.saveServerInfo(serverInfo)
        }
    }
    private fun showSaveButton() {
        _uiState.update { it.copy( enableSaveButton = _uiState.value.isPortValid && _uiState.value.hasChanged)}
    }

    private fun validate() {
        val state = _uiState.value

        val ipError = if (!isValidIP(state.ip)) R.string.settings_ip_error else null

        val portInt = state.port.toIntOrNull()
        val portError = when{
            state.port.isBlank() -> R.string.settings_port_required_error
            portInt == null -> R.string.settings_port_number_error

            // Check if the port requested it between 1000 and 65536
            portInt !in 1000..65535 -> R.string.settings_port_range_error
            else -> null
        }

        val isValid = ipError == null && portError == null

        if (
            state.portError != portError ||
            state.ipError != ipError ||
            state.isPortValid != isValid
        ) {
            _uiState.update {
                it.copy(
                    portError = portError,
                    ipError = ipError,
                    isPortValid = isValid
                )
            }
        }
    }
}