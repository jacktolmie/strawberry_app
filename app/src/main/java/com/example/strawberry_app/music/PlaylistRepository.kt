package com.example.strawberry_app.music

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
    val currentPlaylist: Long = -1,
    val activePlaylist: Long = -1,
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
            if (state.currentPlaylist == -1L || state.currentSongIndex == -1L){
                flowOf(null)
            }else {
                playlistSongDao.observeSongAtPosition(state.currentPlaylist,
                    state.currentSongIndex
                )
            }
        }
    }

    fun sendCommand(command: OutgoingMessage) {
        scope.launch {
            networkManager.sendCommand(command)
        }
    }

    fun updateCurrentSong(playlistId: Long, songIndex: Long) {
        _playlistState.update { it.copy(currentPlaylist = playlistId, currentSongIndex = songIndex) }
    }

    fun getSongById(id: Long) : Flow<SongEntity?> = songDao.observeById(id)

    // Playlist database changes.
    suspend fun makeAllPlaylists(playlists: List<Playlist>){
        playlistDao.deleteAll()

        playlists.forEach { playlist ->

            playlistDao.insert(PlaylistEntity(id = playlist.id, favourite = playlist.favourite, name = playlist.name))

            val songs = playlist.songs.map { song ->
                SongEntity(id = song.id, title = song.title, artist = song.artist, album = song.album, length = song.length)
            }
            songDao.insertAll(songs)

            val songIndex = playlist.songs.mapIndexed { index, song ->
                PlaylistSongEntity(playlistId = playlist.id, songId = song.id, position = index)
            }
            playlistSongDao.insertAll(entities = songIndex)
        }
    }

    suspend fun makeCurrentPlaylist(playlist: Playlist){
        playlistDao.insert(PlaylistEntity(id = playlist.id, favourite = playlist.favourite, name = playlist.name))
    }

    suspend fun serverFavourite(id: Long, isFavourite: Boolean){
        playlistDao.updateFavourite(id = id, favourite = isFavourite)
    }

    suspend fun updatePlaylist(playlist: Playlist){
        playlistSongDao.delete(playlistId = playlist.id)
        val songs = playlist.songs.map{ song ->
            SongEntity(
                id = song.id,
                title = song.title,
                artist = song.artist,
                album = song.album,
                length = song.length)
        }
        songDao.insertAll(songs = songs)

        val playlistEntity = PlaylistEntity(
            id = playlist.id,
            favourite = playlist.favourite,
            name = playlist.name)

        val songIndex = playlist.songs.mapIndexed { index, song ->
            PlaylistSongEntity(
                playlistId = playlist.id,
                songId = song.id,
                position = index)
        }
        playlistSongDao.insertAll( entities = songIndex)

        playlistDao.updatePlaylist(playlist = playlistEntity)
    }

    // Functions to change/receive server playlists.
    fun clearCurrentPlaylist(id: Long) = sendCommand(OutgoingMessage.ClearPlaylist(id))
    fun closeCurrentPlaylist(id: Long) = sendCommand(OutgoingMessage.CloseCurrent(id))
    fun deleteCurrentPlaylist(playlistId: Long) = sendCommand(OutgoingMessage.DeleteCurrentPlaylist(playlistId))
    fun removeSongsCurrentPlaylist(id: Long, songsList: List<Long>) =
        sendCommand(OutgoingMessage.RemoveCurrentSongsFromPlaylist(id = id, songsList = songsList))
    fun removeDuplicatesInPlaylist() = sendCommand(OutgoingMessage.RemoveDuplicatesFromPlaylist)
    fun renameCurrentPlaylist(id: Long, name:String) = sendCommand(OutgoingMessage.RenamePlaylist(id = id, name = name))
    fun sendActivePlaylistSong(id: Long, songIndex: Long) {
        sendCommand(OutgoingMessage.SendActivePlaylistSong(id = id, songIndex = songIndex))
    }
    fun sendAllPlaylists(playlists: List<Playlist>) = sendCommand(OutgoingMessage.SendAllPlaylists(playlists = playlists))
    fun sendCurrentPlaylist(id: Long, playlist: Playlist) {
        sendCommand(OutgoingMessage.SendCurrentPlaylist(id = id, playlist =  playlist))
    }
    fun sendPlaylistFavourite(id: Long, favourite: Boolean) =
        sendCommand(OutgoingMessage.FavouritePlaylist(playlistId = id, favourite = favourite))
    fun setCurrentPlaylist(id: Long): Unit = sendCommand(OutgoingMessage.SetCurrentPlaylist(id = id))
    fun shuffleAllPlaylists() = sendCommand(OutgoingMessage.ShuffleAllPlaylists)
    fun shuffleCurrentPlaylist(id: Long) = sendCommand(OutgoingMessage.ShuffleCurrentPlaylist(id = id))

    fun serverRenamedPlaylist(id: Long, name: String) {
        TODO() // create function to rename local name on db
    }
}