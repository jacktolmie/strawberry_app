package com.example.strawberry_app.screens

import android.content.Context
import android.util.Base64
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.OutgoingMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun getImageFile(filename: String): File {
        return File(albumArtDir, filename)
    }

    fun hasImage(name: String): Boolean{
        return File(albumArtDir, name).exists()
    }

    fun receiveCover(name: String, image: String){
        val bytes = Base64.decode(image, Base64.DEFAULT)
        val file = getImageFile(name)
        file.writeBytes(bytes)
    }

    fun getAlbumArtFile(name: String): File? {
        return if (hasImage(name)) {
            getImageFile(name)
        } else {
            null
        }
    }

    fun notifyAlbumArtReady(name: String){
        _latestCover.value = name
    }

    fun checkAlbumArt(name: String){
        if (name.isEmpty()){
            notifyAlbumArtReady("")
            return
        }
        if (!hasImage(name)){
            scope.launch { networkManager.sendCommand(OutgoingMessage.RequestCover) }
            return
        }
        notifyAlbumArtReady(name)
    }
}