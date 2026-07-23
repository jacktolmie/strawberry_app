package com.example.strawberry_app.screens

import android.content.Context
import android.util.Base64
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AlbumArtRepository @Inject constructor(
    @param:ApplicationContext
    private val context: Context,
    private val networkManager: NetworkManager,
    @param:ApplicationScope
    private val scope: CoroutineScope
) {
    private val _latestCover = MutableStateFlow("")
    val latestCover = _latestCover.asStateFlow()

    private val albumArtDir : File = File(context.filesDir, "album_art")
        .also{ if(!it.exists()) it.mkdirs()}

    val requestedCovers = mutableSetOf<String>()

    private val _albumArtCollection = MutableStateFlow<Map<String, File?>>(emptyMap())

    fun checkAlbumArt(name: String){
        if (name.isEmpty()){
            notifyAlbumArtReady("")
            return
        }
        if (!hasImage(name)){
            scope.launch { networkManager.sendCommand(
                OutgoingMessage.RequestCover(name) )
            }
            return
        }
        notifyAlbumArtReady(name)
    }

    fun getAlbumArtFile(coverArt: String): File? {
        if (requestedCovers.contains(coverArt)) return null

        val fileName = File(coverArt).name

        return if (hasImage(fileName)) {
            getImageFile(fileName)
        } else {
            requestedCovers.add(coverArt)
            requestCover(coverArt)
            null
        }
    }

    fun getImageFile(filename: String): File {
        return File(albumArtDir, filename)
    }

    fun hasImage(name: String): Boolean{
        return File(albumArtDir, name).exists()
    }

    fun notifyAlbumArtReady(name: String){
        _latestCover.value = name
    }

    fun receiveCover(name: String, coverImage: String) {
        val bytes = Base64.decode(coverImage, Base64.DEFAULT)
        val file = getImageFile(File(name).name)

        file.writeBytes(bytes)

        _albumArtCollection.update { it + (name to file) }
    }

    fun removeRequestedArt(name: String){
        requestedCovers.removeIf { it == name }
    }

    fun requestCover(coverArt: String){
        scope.launch {
            networkManager.sendCommand(OutgoingMessage.RequestCover(coverArt))
        }
    }
}