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

    // Should I make defaults, or force callers to provide playlistID and row???
    fun getAlbumArtFile(coverArt: String, playlistId: Long = -1L, row: Long = -1L): File? {
        val file = File(albumArtDir, coverArt)
        println("playlist_song Looking for art: ${file.absolutePath} exists: ${file.exists()}")
        return if (hasImage(coverArt)) {
            getImageFile(coverArt)
        } else {
            if (playlistId == -1L || row == -1L) return null
            println("playlist_song No album art loaded. Get image?")
            requestCover(
                playlistId = playlistId,
                row = row
            )
            null // null or get the name sent from server?
        }
    }

    fun notifyAlbumArtReady(name: String){
        _latestCover.value = name
    }

    fun checkAlbumArt(playlistId: Long, row: Long, name: String){
        if (name.isEmpty()){
            notifyAlbumArtReady("")
            return
        }
        if (!hasImage(name)){
            requestCover(
                playlistId = playlistId,
                row = row
            )
            scope.launch { networkManager.sendCommand(
                OutgoingMessage.RequestCover(playlistId = playlistId, row = row))
            }
            return
        }
        notifyAlbumArtReady(name)
    }

     fun requestCover(playlistId: Long, row: Long){
        scope.launch {
            networkManager.sendCommand(
                OutgoingMessage.RequestCover(
                    playlistId = playlistId,
                    row = row
                )
            )
        }
    }
}