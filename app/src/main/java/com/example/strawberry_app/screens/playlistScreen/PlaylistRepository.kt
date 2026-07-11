package com.example.strawberry_app.screens.playlistScreen

import androidx.room.withTransaction
import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.data.dao.SongDao
import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.data.db.AppDatabase
import com.example.strawberry_app.data.entity.PlaylistEntity
import com.example.strawberry_app.data.entity.PlaylistSongEntity
import com.example.strawberry_app.data.entity.SongEntity
import com.example.strawberry_app.music.Playlist
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
    private val db: AppDatabase,
    @ApplicationScope
    private val scope: CoroutineScope
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
        scope.launch {
            _playlistState.update {
                it.copy(
                    activePlaylist = it.activePlaylist,
                    currentPlaylist = it.currentPlaylist
                )
            }
        }
    }

    // Send playlist commands to network manager.
    fun sendCommand(command: OutgoingMessage) {
        scope.launch { networkManager.sendCommand(command) }
    }



    // Playlist database changes.
    suspend fun makeAllPlaylists(playlists: List<Playlist>) {
        db.withTransaction {
            playlistDao.deleteAll()

            playlists.forEach { playlist ->
                playlist.songs.forEach { song ->
                }
                playlistDao.insert(
                    PlaylistEntity(
                        id = playlist.id,
                        favourite = playlist.favourite,
                        name = playlist.name,
                        playlistLength = playlist.playlistLength,
                        playlistSize = playlist.playlistSize
                    )
                )

                val songs = playlist.songs.map { song ->
                    SongEntity(
                        id = song.id,
                        url = song.url,
                        title = song.title,
                        artist = song.artist,
                        album = song.album,
                        coverImage = song.coverImage,
                        length = song.length
                    )
                }
                songDao.insertAll(songs)

                val songIndex = playlist.songs.mapIndexed { index, song ->
                    PlaylistSongEntity(
                        playlistId = playlist.id,
                        position = index,
                        songUrl = song.url
                    )
                }
                playlistSongDao.insertAll(entities = songIndex)
            }
        }

    }

    suspend fun serverFavourite(id: Long, isFavourite: Boolean) {
        playlistDao.updateFavourite(id = id, favourite = isFavourite)
    }

    suspend fun makePlaylist(playlist: Playlist) {
        playlistDao.upsertPlaylist(
            PlaylistEntity(
                id = playlist.id,
                favourite = playlist.favourite,
                name = playlist.name,
                playlistLength = playlist.playlistLength,
                playlistSize = playlist.playlistSize
            )
        )

        playlistSongDao.delete(playlistId = playlist.id)

        val songs = playlist.songs.map { song ->
            SongEntity(
                id = song.id,
                url = song.url,
                title = song.title,
                artist = song.artist,
                album = song.album,
                coverImage = song.coverImage,
                length = song.length
            )
        }
        songDao.insertAll(songs = songs)

        val songIndex = playlist.songs.mapIndexed { index, song ->
            PlaylistSongEntity(
                playlistId = playlist.id,
                songUrl = song.url,
                position = index
            )
        }
        playlistSongDao.insertAll(entities = songIndex)
    }

    suspend fun serverClosedPlaylist(id: Long) {
        playlistDao.deleteById(id = id)
    }

    suspend fun serverRenamedPlaylist(id: Long, name: String) {
        playlistDao.updateName(id = id, name = name)
    }

    // Get information about playlists and songs in each one.
    fun getPlaylists(): Flow<List<PlaylistEntity>>{
        return playlistDao.observeAll()
    }

    fun getPlaylistSongs(id: Long): Flow<List<SongWithPosition>>{
        return playlistSongDao.observeSongsForPlaylist(id)
    }

    fun updatePlaylistState(activePlaylist: Long, currentPlaylist: Long) {
        _playlistState.update {
            it.copy(activePlaylist = activePlaylist, currentPlaylist = currentPlaylist)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getCurrentSong(): Flow<SongWithPosition?> {
        return playlistState.flatMapLatest { state ->
            if (state.currentPlaylist == -1L || state.currentSongIndex == -1L) {
                flowOf(null)
            } else {
                playlistSongDao.observeSongAtPosition(
                    state.currentPlaylist,
                    state.currentSongIndex
                )
            }
        }
    }

    fun updateCurrentSong(playlistId: Long, songIndex: Long) {
        _playlistState.update {
            it.copy(
                currentPlaylist = playlistId,
                currentSongIndex = songIndex
            )
        }
    }
}