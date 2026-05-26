package com.example.strawberry_app.music

import android.util.Log
import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.data.dao.SongDao
import com.example.strawberry_app.data.db.AppDatabase
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import com.example.strawberry_app.data.entity.SongEntity
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.protocol.EventType
import com.example.strawberry_app.network.protocol.IncomingMessage
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Singleton
class MusicRepository @Inject constructor(
    private val serverMessages: SharedFlow<@JvmSuppressWildcards IncomingMessage>,
    private val playlistDao: PlaylistDao,
    private val songDao: SongDao,
    private val playlistSongDao: PlaylistSongDao,
    @ApplicationScope private val scope: CoroutineScope
) {
    init {
        Log.i("MusicRepository", "Repository created, starting collection")
        scope.launch {
            serverMessages
                .distinctUntilChanged()
                .collectLatest { info ->
                    Log.i("MusicRepository", "Got message: $info")
                    when(info){
                        is EventType.GuiUpdates -> {
                            when (info.playlists) {
                                is EventType.MakeAllPlaylists -> makeAllPlaylists(info.playlists.playlists)
                            }
                        }
                        is EventType.MakeCurrentPlaylist -> makeCurrentPlaylist(info.playlist)
                        else -> Unit
            } }
        }
    }

    private suspend fun makeAllPlaylists(playlists: List<Playlist>){
        playlistDao.deleteAll()
        Log.i("MusicRepository", "makeAllPlaylists called with ${playlists.size} playlists")

        playlists.forEachIndexed { index, playlist ->

            playlistDao.insert(PlaylistEntity(playlist.id, playlist.name))
            Log.i("MusicRepository", "Inserted playlist: ${playlist.id} ${playlist.name}")

            val songs = playlist.songs.map { song ->
                SongEntity(song.id, song.title, song.artist, song.album, song.length)
            }
            songDao.insertAll(songs)

            val songIndex = playlist.songs.mapIndexed { index, song ->
                PlaylistSongEntity(playlist.id, song.id, index)
            }
            playlistSongDao.insertAll(songIndex)
        }
    }

    private suspend fun makeCurrentPlaylist(playlist: Playlist){
        playlistDao.insert(PlaylistEntity(playlist.id, playlist.name))
        Log.i("MusicRepository", "Playlist: ${playlist.name}, ${playlist.id}, ${playlist.songs}")
    }
}