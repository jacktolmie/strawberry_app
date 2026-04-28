package com.example.strawberry_app.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.server.ServerInfo
import com.example.strawberry_app.server.ServerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    private val _lastServerMessage = MutableLiveData<String>()
    val lastServerMessage: LiveData<String> = _lastServerMessage

    init {
//        serverViewModel.serverInfo
        serverRepository.serverInfoFlow
            .onEach { serverInfo ->
                serverInfo.let {
                    android.util.Log.d("ConnectionViewModel", "Server info changed. Attempting to connect...")
                    connect(it)
                }
            }
            .launchIn(viewModelScope)

        networkManager.serverMessages
            .onEach { message ->
                _lastServerMessage.postValue(message)
                if (message.contains("disconnected")) {
                    _isConnected.postValue(false)
                }
            }
            .launchIn(viewModelScope)
    }

    fun connect(serverInfo: ServerInfo?) {
        if (serverInfo == null) {
            _isConnected.postValue(false)
            return
        }
        viewModelScope.launch {
            val success = networkManager.connect( serverInfo)
//            _isConnected.postValue(success)
        }
    }

    fun sendCommand(command: String) {
        viewModelScope.launch {
            networkManager.sendCommand("""{"command":"$command"}""")
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            networkManager.disconnect()
        }
    }
}