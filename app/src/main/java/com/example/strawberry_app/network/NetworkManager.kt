package com.example.strawberry_app.network

import android.util.Log
import com.example.strawberry_app.network.protocol.IncomingMessage
import com.example.strawberry_app.server.ServerInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor() {

    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionStateFlow: StateFlow<ConnectionState> = _connectionState
    private var currentServerInfo: ServerInfo = ServerInfo()

    private val connectionMutex = Mutex()
    private var dataOutputStream: DataOutputStream? = null

    private val json = Json{ ignoreUnknownKeys = true; classDiscriminator = "type"}
    private var listenerJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO )
    private var reconnectJob: Job? = null
    private var shouldReconnect = true
    private var socket: Socket? = null
    private val _serverMessages =
        MutableSharedFlow<IncomingMessage>(replay = 0, extraBufferCapacity = 64)

    val serverMessages = _serverMessages.asSharedFlow()

    companion object {
        private const val MAX_MESSAGE_SIZE = 10000
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }

    suspend fun connect(serverInfo: ServerInfo) {
        connectionMutex.withLock {
            currentServerInfo = serverInfo

            shouldReconnect = true
            reconnectJob?.cancel()

            if (_connectionState.value is ConnectionState.Connecting ||
                _connectionState.value is ConnectionState.Connected
            ) return

            if (socket?.isConnected == true && socket?.isClosed == false) {
                disconnect(updateStatus = false)
            }

            _connectionState.value = ConnectionState.Connecting

            try {
                Log.i("NetworkManager", "Connecting to server")
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
                } else {
                    Log.i("NetworkManager", "Failed connecting to server")
                    _connectionState.value = ConnectionState.Error("Auth failed")
                    closeResources()
                    scheduleReconnect(serverInfo)
                }

            } catch (e: Exception) {
                Log.i("NetworkManager", "Error: $e")
                _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
                closeResources()
                scheduleReconnect(serverInfo)
            }
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
        shouldReconnect = false
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

                if (shouldReconnect && currentServerInfo.ip.isNotBlank()) {
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
//                    disconnect()
                    _connectionState.value = ConnectionState.Error(e.message.toString())
                    closeResources()
                    scheduleReconnect(currentServerInfo)
                }
            } else {
                Log.d("NetworkManager", "Cannot send command, not connected.")
            }
        }
    }

    private fun scheduleReconnect(serverInfo: ServerInfo){
        if(!shouldReconnect || reconnectJob?.isActive == true) return

        reconnectJob = scope.launch {
            delay(3000)
            if(shouldReconnect) connect(serverInfo)
        }
    }
}