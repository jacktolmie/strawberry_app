package com.example.strawberry_app.network

import android.util.Log
import com.example.strawberry_app.server.ServerInfo
import com.example.strawberry_app.server.ServerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

private const val MAX_MESSAGE_SIZE = 10 * 1024 * 1024

@Singleton
class NetworkManager @Inject constructor(private val serverRepository: ServerRepository){

    private var socket: Socket? = null
    private var dataOutputStream: DataOutputStream? = null
    private var listenerJob: Job? = null

    // A flow to broadcast received server messages to any listeners (like your ViewModel).
    private val _serverMessages = MutableSharedFlow<String>(replay = 0, extraBufferCapacity = 64)

    val serverMessages = _serverMessages.asSharedFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState

    fun start(){
        scope.launch {
            serverRepository.serverInfoFlow
                .distinctUntilChanged()
                .collect { serverInfo ->
                    connect(serverInfo)
                }
        }
    }

    fun connect(serverInfo: ServerInfo) {
        scope.launch {
            _connectionState.value = ConnectionState.Connecting

            if (socket?.isConnected == true && socket?.isClosed == false) {
                disconnect()
            }

            try {
                Log.i("NetworkManager", "Connecting to server")
                val connectedSocket = Socket(serverInfo.ip, serverInfo.port)
                socket = connectedSocket

                val inputStream = DataInputStream(connectedSocket.getInputStream())
                val outputStream = DataOutputStream(connectedSocket.getOutputStream())

                val isAuthenticated = authenticate(serverInfo, inputStream, connectedSocket)

                if (isAuthenticated) {
                    dataOutputStream = outputStream
                    _connectionState.value = ConnectionState.Connected
                    startListening(inputStream)
                } else {
                    Log.i("NetworkManager", "Failed connecting to server")
                    _connectionState.value = ConnectionState.Error("Auth failed")
                    disconnect()
                }

            } catch (e: Exception) {
                Log.i("NetworkManager", "Error: $e")
                _connectionState.value = ConnectionState.Error(e.message ?: "Unknown error")
                disconnect()
            }
        }
    }

    private fun startListening(dataInputStream: DataInputStream){
        listenerJob?.cancel()

        listenerJob = scope.launch {
            try {
                while (isActive && socket?.isConnected == true) {
                    val length = dataInputStream.readInt()

                    if (length in 1..<MAX_MESSAGE_SIZE) {
                        val messageBytes = ByteArray(length)
                        dataInputStream.readFully(messageBytes)
                        val jsonString = String(messageBytes, Charsets.UTF_8)
                        println("Server is sending: $jsonString") // Delete later

                        _serverMessages.emit(jsonString)

                    } else {
                        Log.e("NetworkManager", "Error: Invalid message length received: $length. Resetting connection.")
                        break
                    }
                }
            } catch (e: Exception) {
                // This will catch read errors, like the server closing the connection
                Log.e("NetworkManager",  "Listener error: ${e.message}")
            } finally {
                // This runs when the loop breaks or an exception occurs
                disconnect()
            }
        }
    }

    // --- 4. Function to Send Commands ---
    suspend fun sendCommand(jsonCommand: String) {
        // Use withContext for the I/O operation of writing to the socket
        withContext(Dispatchers.IO) {
            if (dataOutputStream != null && socket?.isConnected == true) {
                try {
                    val messageBytes = jsonCommand.toByteArray(Charsets.UTF_8)
                    dataOutputStream?.writeInt(messageBytes.size)
                    dataOutputStream?.write(messageBytes)
                    dataOutputStream?.flush()
                } catch (e: Exception) {
                    Log.e("NetworkManager", "Failed to send command: ${e.message}")
                    disconnect()
                }
            } else {
                Log.d("NetworkManager", "Cannot send command, not connected.")
            }
        }
    }

    // --- 5. Disconnect and Cleanup ---
    fun disconnect() {
        scope.launch {
            listenerJob?.cancelAndJoin()
            listenerJob = null

            try {
                dataOutputStream?.close()
                socket?.close()
            } catch (e: Exception) {
                Log.e("NetworkManager", "Error while disconnecting: ${e.message}")
            } finally {
                socket = null
                dataOutputStream = null

                _connectionState.value = ConnectionState.Disconnected
            }
        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }

    // Probably not needed. Delete if that is the case.
//    fun shutdown(){
//        coroutineScope.cancel()
//    }
}