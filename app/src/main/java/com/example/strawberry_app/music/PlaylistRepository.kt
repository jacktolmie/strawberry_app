package com.example.strawberry_app.music

import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.data.dao.SongDao
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import com.example.strawberry_app.data.entity.SongEntity
import com.example.strawberry_app.network.ApplicationScope
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlaylistState(
    val currentPlaylist: Int = -1,
    val activePlaylist: Int = -1,
    val currentSongIndex: Long = -1,
    val currentSongData: SongWithPosition? = null
)

@Singleton
class PlaylistRepository @Inject constructor(
    private val playlistDao: PlaylistDao,
    private val songDao: SongDao,
    private val playlistSongDao: PlaylistSongDao,
    @ApplicationScope private val scope: CoroutineScope
) {

    private val _playlistState = MutableStateFlow(PlaylistState())
    val playlistState = _playlistState.asStateFlow()

    val currentSongData: Flow<SongWithPosition?> = getCurrentSong()

    init {
        scope.launch {
            getCurrentSong().collect { songData ->
                _playlistState.update { it.copy(currentSongData = songData) }
            }
        }
    }

    suspend fun makeAllPlaylists(playlists: List<Playlist>){
        playlistDao.deleteAll()

        playlists.forEach { playlist ->

            playlistDao.insert(PlaylistEntity(playlist.id, playlist.name))

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

    suspend fun makeCurrentPlaylist(playlist: Playlist){
        playlistDao.insert(PlaylistEntity(playlist.id, playlist.name))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCurrentSong(): Flow<SongWithPosition?> {
        return playlistState.flatMapLatest { state ->
            if (state.currentPlaylist == -1 || state.currentSongIndex == -1L){
                flowOf(null)
            }else {
                playlistSongDao.observeSongAtPosition(state.currentPlaylist,
                    state.currentSongIndex
                )
            }
        }
    }

    fun updateCurrentSong(playlistId: Int, songIndex: Long) {
        _playlistState.update { it.copy(currentPlaylist = playlistId, currentSongIndex = songIndex) }
    }

    fun getSongById(id: Long) : Flow<SongEntity?> = songDao.observeById(id)
}