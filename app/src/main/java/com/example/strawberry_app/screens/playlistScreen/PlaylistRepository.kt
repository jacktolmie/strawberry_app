package com.example.strawberry_app.screens.playlistScreen

import com.example.strawberry_app.data.dao.PlaylistDao
import com.example.strawberry_app.data.dao.PlaylistSongDao
import com.example.strawberry_app.data.dao.SongDao
import com.example.strawberry_app.data.dao.SongWithPosition
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

    fun sendCommand(command: OutgoingMessage) {
        scope.launch { networkManager.sendCommand(command) }
    }

    fun updateCurrentSong(playlistId: Long, songIndex: Long) {
        _playlistState.update {
            it.copy(
                currentPlaylist = playlistId,
                currentSongIndex = songIndex
            )
        }
    }

    // Playlist database changes.
    suspend fun makeAllPlaylists(playlists: List<Playlist>) {
        playlistDao.deleteAll()

        playlists.forEach { playlist ->
            println("playlist Writing playlist: ${playlist.name} ${playlist.id}) with ${playlist.songs.size} songs")
            playlist.songs.forEach { song ->
                println("playlist  song id=${song.id} title=${song.title}")
            }
            playlistDao.insert(
                PlaylistEntity(
                    id = playlist.id,
                    favourite = playlist.favourite,
                    name = playlist.name
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

    suspend fun serverFavourite(id: Long, isFavourite: Boolean) {
        playlistDao.updateFavourite(id = id, favourite = isFavourite)
    }

    suspend fun makePlaylist(playlist: Playlist) {
        playlistDao.upsertPlaylist(
            PlaylistEntity(
                id = playlist.id,
                favourite = playlist.favourite,
                name = playlist.name
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
}