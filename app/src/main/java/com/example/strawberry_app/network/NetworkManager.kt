package com.example.strawberry_app.network

import android.util.Log
import com.example.strawberry_app.network.protocol.IncomingMessage
import com.example.strawberry_app.network.protocol.OutgoingMessage
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class NetworkManager @Inject constructor(
    private val repository: ServerRepository,
    @ApplicationScope private val scope: CoroutineScope
) {
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionStateFlow: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private var currentServerInfo: ServerInfo? = null
    private val connectionMutex = Mutex()
    private var dataOutputStream: DataOutputStream? = null

    private val json = Json{ ignoreUnknownKeys = true}
    private var listenerJob: Job? = null
    private var reconnectJob: Job? = null
    private var _shouldReconnect = MutableStateFlow(true)

    private var socket: Socket? = null
    private val _serverMessages =
        MutableSharedFlow<IncomingMessage>(replay = 1, extraBufferCapacity = 64)

    val serverMessages = _serverMessages.asSharedFlow()
var reconnectCount = 0L
    var reconnectTimer = 0L

    private val delayTime = MutableStateFlow<Long>(3000)

    init {

        scope.launch {
            repository.serverInfoFlow
                .distinctUntilChanged()
                .collectLatest { info ->
                if (info == null) {
                    disconnect()
                    return@collectLatest
                }
                    disconnect(false)
                    connect(info)
            }
        }
    }
    companion object {
        private const val MAX_MESSAGE_SIZE = 1_048_576
        private const val MAX_CONNECTION_ATTEMPTS = 11
    }

    suspend fun connect(serverInfo: ServerInfo?) {
        connectionMutex.withLock {
            if (serverInfo == null) {
                Log.e("NetworkManager", "No server info provided")
                return
            }

            if (serverInfo.ip.isBlank()) {
                Log.e("NetworkManager", "Server IP is blank")
                return
            }

            if (_connectionState.value == ConnectionState.Connecting) return

            currentServerInfo = serverInfo

            _shouldReconnect.value = true

            if (socket?.isConnected == true && socket?.isClosed == false) {
                disconnect(updateStatus = false)
            }

            _connectionState.value = ConnectionState.Connecting

            try {
                Log.i(
                    "NetworkManager",
                    "Connecting to server ${serverInfo.ip} on port ${serverInfo.port}"
                )
                val connectedSocket = withContext(Dispatchers.IO) {
                    Socket().apply {
                        connect(InetSocketAddress(serverInfo.ip, serverInfo.port), 5000)
                    }
                }

                socket = connectedSocket

                val inputStream =
                    withContext(Dispatchers.IO) { DataInputStream(connectedSocket.getInputStream()) }
                val outputStream =
                    withContext(Dispatchers.IO) { DataOutputStream(connectedSocket.getOutputStream()) }

                val isAuthenticated = withContext(Dispatchers.IO) {
                    authenticate(
                        serverInfo,
                        connectedSocket,
                        inputStream,
                        outputStream
                    )
                }

                if (isAuthenticated) {
                    dataOutputStream = outputStream
                    _connectionState.value = ConnectionState.Connected

                    val shouldReconnectAfter = startListening(inputStream)

                    resetReconnects()
                    closeResources()

                    if (shouldReconnectAfter) startReconnectLoop(currentServerInfo)

                } else {
                    Log.i("NetworkManager", "Failed connecting to server")
                    _connectionState.value = ConnectionState.Error("Authentication failed")
                    closeResources()
                    startReconnectLoop(currentServerInfo)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("NetworkManager", "Error: ${e.message}")
                if (e.message.toString().contains("Connection refused")) {
                    Log.e("NetworkManager", "Strawberry not running?")
                    _connectionState.value = ConnectionState.Error("Player not responding")
                } else _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
                closeResources()
                startReconnectLoop(currentServerInfo)
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
        _shouldReconnect.value = false
        reconnectJob?.cancelAndJoin()
        reconnectJob = null

        closeResources()
        if (updateStatus) _connectionState.value = ConnectionState.Disconnected
    }

    private suspend fun startListening(dataInputStream: DataInputStream): Boolean {
        listenerJob?.cancelAndJoin()

        listenerJob = scope.launch {
            try {
                while (isActive && socket?.isClosed == false) {
                    val length = dataInputStream.readInt()

                    if (length in 1..<MAX_MESSAGE_SIZE) {
                        val messageBytes = ByteArray(length)
                        dataInputStream.readFully(messageBytes)
                        val jsonString = String(messageBytes, Charsets.UTF_8)

                        try {
                            val parsedMessage = json.decodeFromString<IncomingMessage>(jsonString)
                            Log.e("NetworkManager", "Unparsed message $jsonString") // Delete when done testing
//                            Log.e("NetworkManager", "Server sent: $parsedMessage") // Delete when done testing
                            _serverMessages.tryEmit(parsedMessage)
                        } catch (e: SerializationException) {
                            Log.e("NetworkManager", "Failed to parse message: $jsonString — ${e.message}")
                        }
                    } else {
                        Log.e(
                            "NetworkManager",
                            "Error: Invalid message length received: $length. Resetting connection."
                        )
                        break
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // This will catch read errors, like the server closing the connection
                Log.e("NetworkManager", "Listener error: ${e.message}")
            } finally {
                Log.d("NetworkManager", "Listener coroutine exiting")
                if (_connectionState.value !is ConnectionState.Disconnected) {
                    _connectionState.value = ConnectionState.Disconnected
                }
            }
        }
        listenerJob?.join()

        return _shouldReconnect.value && currentServerInfo?.ip?.isNotBlank() == true && _connectionState.value !is ConnectionState.Disconnected
    }

    suspend fun sendCommand(command: OutgoingMessage) {

        withContext(Dispatchers.IO) {
            if (dataOutputStream != null && socket?.isConnected == true) {
                try {
                    val jsonCommand = Json.encodeToString(OutgoingMessage.serializer(), command)
                    val messageBytes = jsonCommand.toByteArray(Charsets.UTF_8)
                    dataOutputStream?.writeInt(messageBytes.size)
                    dataOutputStream?.write(messageBytes)
                    dataOutputStream?.flush()
                } catch (e: Exception) {
                    Log.e("NetworkManager", "Failed to send command: ${e.message}")
                    _connectionState.value = ConnectionState.Error(e.message.toString())
                    closeResources()
                    startReconnectLoop(currentServerInfo)
                }
            } else {
                Log.d("NetworkManager", "Cannot send command, not connected.")
            }
        }
    }

    private fun startReconnectLoop(serverInfo: ServerInfo?){

        if(!_shouldReconnect.value || reconnectJob?.isActive == true) return

        reconnectJob = scope.launch {

            while (reconnectCount < MAX_CONNECTION_ATTEMPTS){
                if(!_shouldReconnect.value ||
                    _connectionState.value == ConnectionState.Connected
                ) return@launch

                var countDown = delayTime.value

                while(countDown > 0){
                    reconnectTimer = countDown
                    _connectionState.value = ConnectionState.Reconnecting(reconnectCount, reconnectTimer/ 1000)
                    delay(1000.milliseconds)
                    countDown -= 1000
                }
                if(serverInfo != null){
                    connect(currentServerInfo)
                }

                delayTime.value += 2000
                reconnectCount += 1
            }
        }
    }

    fun manualConnect() {
        scope.launch {
            connect(currentServerInfo)
        }
    }
    fun manualDisconnect() {
        scope.launch {
            disconnect(true)
        }
    }

    private fun resetReconnects(){
        reconnectTimer = 0
        reconnectCount = 0
        delayTime.value = 3000
    }
}