package com.example.strawberry_app.screens.playerScreen

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject


class AlbumArtRepository @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    private val albumArtDir : File = File(context.filesDir, "album_art")
        .also{ if(!it.exists()) it.mkdirs()}

    fun getImageFile(filename: String): File {
        return File(albumArtDir, filename)
    }

    fun hasImage(name: String): Boolean{
        return File(albumArtDir, name).exists()
    }

    fun receiveCover(name: String, image: String){
        val bytes = android.util.Base64.decode(image, android.util.Base64.DEFAULT)
        val file = getImageFile(name)
        file.writeBytes(bytes)
    }
}