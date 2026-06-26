package com.example.strawberry_app.music

import androidx.lifecycle.viewModelScope
import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.data.dao.SongDao
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import com.example.strawberry_app.data.entity.SongEntity
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.OutgoingMessage
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
    private val networkManager: NetworkManager,
    private val playlistDao: PlaylistDao,
    private val playlistSongDao: PlaylistSongDao,
    private val songDao: SongDao,

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

    fun getSongById(id: Long) : Flow<SongEntity?> = songDao.observeById(id)

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

    fun sendCommand(command: OutgoingMessage) {
        scope.launch {
            networkManager.sendCommand(command)
        }
    }

    fun updateCurrentSong(playlistId: Int, songIndex: Long) {
        _playlistState.update { it.copy(currentPlaylist = playlistId, currentSongIndex = songIndex) }
    }

    // Functions to change server playlists.
    fun clearCurrentPlaylist(id: Long) = sendCommand(OutgoingMessage.ClearPlaylist(id))
    fun closeCurrentPlaylist(id: Long) = sendCommand(OutgoingMessage.CloseCurrent(id))
    fun deleteCurrentPlaylist(playlistId: Long) = sendCommand(OutgoingMessage.DeleteCurrentPlaylist(playlistId))
    fun deleteSongsPlaylist(playlistId: Long, songs: List<Long>){
        sendCommand(
            OutgoingMessage.RemoveCurrentSongFromPlaylist(
                playlistId = playlistId,
                songsList = songs
            )
        )
    }
    fun makeFavouritePlaylist(id: Long, favourite: Boolean) =
        sendCommand(OutgoingMessage.FavouritePlaylist(playlistId = id, favourite = favourite))
    fun removeSongsCurrentPlaylist(id: Long, songsList: List<Long>){
        // Fill in with required data
    }
    fun removeDuplicatesInPlaylist() = sendCommand(OutgoingMessage.RemoveDuplicatesFromPlaylist)
    fun renameCurrentPlaylist(id: Long, name:String) =
        sendCommand(OutgoingMessage.RenamePlaylist(id = id, name = name))
    fun sendActivePlaylist(id: Long) = sendCommand(OutgoingMessage.SendActivePlaylist(id))
    fun sendAllPlaylists(playlists: List<Playlist>) = sendCommand(OutgoingMessage.SendAllPlaylists(playlists = playlists))
    fun sendCurrentPlaylist(id: Long, playlist: Playlist) = sendCommand(OutgoingMessage.Sen)

}