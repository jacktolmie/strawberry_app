package com.example.strawberry_app.network

import android.util.Log
import com.example.strawberry_app.network.protocol.IncomingMessage
import com.example.strawberry_app.server.ServerInfo
import com.example.strawberry_app.server.ServerRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor(
    private val repository: ServerRepository,
    @ApplicationScope private val scope: CoroutineScope
) {

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionStateFlow: StateFlow<ConnectionState> = _connectionState

    private var currentServerInfo: ServerInfo? = null
    private val connectionMutex = Mutex()
    private var dataOutputStream: DataOutputStream? = null

    private val json = Json{ ignoreUnknownKeys = true; classDiscriminator = "type"}
    private var listenerJob: Job? = null
    private var reconnectJob: Job? = null
    var shouldReconnect = MutableStateFlow(true)
    private var socket: Socket? = null
    private val _serverMessages =
        MutableSharedFlow<IncomingMessage>(replay = 0, extraBufferCapacity = 64)

    val serverMessages = _serverMessages.asSharedFlow()
    private val reconnectCount = MutableStateFlow(0)
    private val reconnectTimer = MutableStateFlow<Long>(0)

    private val delayTime = MutableStateFlow<Long>(3000)

    init {
        scope.launch {
            repository.serverInfoFlow
                .distinctUntilChanged()
                .collectLatest { info ->
                if (info == null) {
                    disconnect()
                } else {
                    connect(info)
                }
            }
            // Delete when done testing connection status
            connectionStateFlow.collect { state ->
                Log.d("ConnectionTest", "Connection state changed: $state")
            }
        }
    }
    companion object {
        private const val MAX_MESSAGE_SIZE = 10000
    }

    suspend fun connect(serverInfo: ServerInfo?) {

        if(serverInfo == null){
            Log.e("NetworkManager", "No server info provided")
            return
        }

        if (serverInfo.ip.isBlank()){
            Log.e("NetworkManager", "Server IP is blank")
            return
        }

        connectionMutex.withLock {

            if (_connectionState.value == ConnectionState.Connecting ||
                _connectionState.value == ConnectionState.Connected
            ) return

            currentServerInfo = serverInfo

            shouldReconnect.value = true

            if (socket?.isConnected == true && socket?.isClosed == false) {
                disconnect(updateStatus = false)
            }

            _connectionState.value = ConnectionState.Connecting
        }

        try {
            // Change to Log.i!!!!!!!!!!!!!!
            Log.e("NetworkManager", "Connecting to server ${serverInfo.ip} on port ${serverInfo.port}")
            val connectedSocket = withContext(Dispatchers.IO){
                Socket(serverInfo.ip, serverInfo.port)
            }

            socket = connectedSocket

            val inputStream = withContext(Dispatchers.IO){ DataInputStream(connectedSocket.getInputStream()) }
            val outputStream = withContext(Dispatchers.IO){ DataOutputStream(connectedSocket.getOutputStream()) }

            val isAuthenticated = authenticate(serverInfo, inputStream, connectedSocket)

            if (isAuthenticated) {
                dataOutputStream = outputStream
                _connectionState.value = ConnectionState.Connected
                startListening(inputStream)
                resetReconnects()
            } else {
                Log.i("NetworkManager", "Failed connecting to server")
                _connectionState.value = ConnectionState.Error("Authentication failed")
                closeResources()
                scheduleReconnect(currentServerInfo)
            }

        } catch (e: CancellationException){
            throw e
        } catch (e: Exception) {
            Log.e("NetworkManager", "Error: ${e.message}")
            if(e.message.toString().contains("Connection refused")){
                Log.e("NetworkManager", "Strawberry not running?")
                _connectionState.value = ConnectionState.Error("Player not responding")
            }
            else _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
            closeResources()
            scheduleReconnect(currentServerInfo)
        }
    }

    suspend fun closeResources() {
        try {

            Log.d("NetworkManager", "Closing socket")

            withContext(Dispatchers.IO) {
                runCatching { socket?.shutdownInput() }
                runCatching { socket?.shutdownOutput() }
                runCatching { dataOutputStream?.close() }
                runCatching { socket?.close() }
            }

            Log.d("NetworkManager", "Closing listener")

            listenerJob?.cancelAndJoin()
            listenerJob = null

            Log.d("NetworkManager", "Resources closed")

        } catch (e: Exception) {
            Log.e("NetworkManager", "Error while closing resources: ${e.message}")
        } finally {
            socket = null
            dataOutputStream = null
        }
    }

    suspend fun disconnect(updateStatus: Boolean = true) {
        shouldReconnect.value = false
        reconnectJob?.cancelAndJoin()
        reconnectJob = null

        closeResources()
        if (updateStatus) _connectionState.value = ConnectionState.Disconnected
    }

    private suspend fun startListening(dataInputStream: DataInputStream) {
        listenerJob?.cancelAndJoin()

        listenerJob = scope.launch {
            try {
                while (isActive && socket?.isClosed == false) {
                    val length = dataInputStream.readInt()

                    if (length in 1..<MAX_MESSAGE_SIZE) {
                        val messageBytes = ByteArray(length)
                        dataInputStream.readFully(messageBytes)
                        val jsonString = String(messageBytes, Charsets.UTF_8)
                        val obj = json.parseToJsonElement(jsonString).jsonObject
                        println("Server is sending: $obj") // Delete later

                        val parsedMessage = json.decodeFromString<IncomingMessage>(jsonString)
                        _serverMessages.tryEmit(parsedMessage)

                    } else {
                        Log.e(
                            "NetworkManager",
                            "Error: Invalid message length received: $length. Resetting connection."
                        )
                        break
                    }
                }
            } catch (e: Exception) {
                // This will catch read errors, like the server closing the connection
                Log.e("NetworkManager", "Listener error: ${e.message}")
            } finally {
                Log.d("NetworkManager", "Listener coroutine exiting")
                if (_connectionState.value !is ConnectionState.Disconnected) {
                    _connectionState.value = ConnectionState.Disconnected
                }

                val info = currentServerInfo
                if (shouldReconnect.value && info != null && info.ip.isNotBlank()) {
                    closeResources()
                    scheduleReconnect(currentServerInfo)
                }
            }
        }
    }

    suspend fun sendCommand(jsonCommand: String) {

        withContext(Dispatchers.IO) {
            if (dataOutputStream != null && socket?.isConnected == true) {
                try {
                    val messageBytes = jsonCommand.toByteArray(Charsets.UTF_8)
                    dataOutputStream?.writeInt(messageBytes.size)
                    dataOutputStream?.write(messageBytes)
                    dataOutputStream?.flush()
                } catch (e: Exception) {
                    Log.e("NetworkManager", "Failed to send command: ${e.message}")
                    _connectionState.value = ConnectionState.Error(e.message.toString())
                    closeResources()
                    scheduleReconnect(currentServerInfo)
                }
            } else {
                Log.d("NetworkManager", "Cannot send command, not connected.")
            }
        }
    }

    private fun scheduleReconnect(serverInfo: ServerInfo?){

        if(!shouldReconnect.value || reconnectJob?.isActive == true) return

        reconnectJob = scope.launch {

            while (reconnectCount.value < 11){
                if(!shouldReconnect.value ||
                    _connectionState.value == ConnectionState.Connected
                ) return@launch

                var countDown = delayTime.value

                while(countDown > 0){
                    reconnectTimer.value = countDown
                    _connectionState.value = ConnectionState.Reconnecting(reconnectCount.value, reconnectTimer.value / 1000)
                    delay(1000)
                    countDown -= 1000
                }
                if(serverInfo != null){
                    connect(currentServerInfo)
                }

                delayTime.value += 2000
                reconnectCount.value += 1
            }
        }
    }

    fun manualDisconnect(disconnectPressed: Boolean){
        scope.launch {
            if(disconnectPressed){
                disconnect()
            } else{
                if(currentServerInfo != null)
                connect(currentServerInfo)
            }

        }
    }

    private fun resetReconnects(){
        reconnectTimer.value = 0
        reconnectCount.value = 0
        delayTime.value = 3000
    }
}


//    private fun hashPasswordWithNonce(
//        password: String,
//        nonce: String
//    ): String {
//
//        val combined = password + nonce
//
//        val hashBytes = MessageDigest
//            .getInstance("SHA-256")
//            .digest(combined.toByteArray())
//
//        return hashBytes.joinToString("") { "%02x".format(it) }
//    }