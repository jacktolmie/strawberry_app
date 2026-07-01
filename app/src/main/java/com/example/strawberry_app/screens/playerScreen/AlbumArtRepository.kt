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

    fun checkForCover(name: String): Boolean{
//        if(!hasImage(name)){
////            requestCover(name)
//        }
        return false
    }
    private fun hasImage(name: String): Boolean{
        return File(albumArtDir, name).exists()
    }

    fun receiveCover(name: String){
//        if(name.isEmpty())
    }

}