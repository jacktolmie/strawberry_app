package com.example.strawberry_app.screens

import android.util.Log
import com.example.strawberry_app.music.PlaylistRepository
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.ErrorType
import com.example.strawberry_app.network.protocol.EventType
import com.example.strawberry_app.network.protocol.ResponseType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val networkManager: NetworkManager,
    private val playlistRepository: PlaylistRepository,
    @ApplicationScope private val scope: CoroutineScope
){
    private val _serverUpdates = MutableStateFlow(ServerGuiValues())
    val serverUpdates = _serverUpdates.asStateFlow()

    private var observeJob: Job? = null

    init {
        scope.launch {
            networkManager.connectionStateFlow.collect { state ->
                if (state is ConnectionState.Disconnected || state is ConnectionState.Connecting){
                    resetServerValues()
                }
                if (state is ConnectionState.Connected){
                    observeNetworkMessages()
                }
            }
        }
        scope.launch {
            playlistRepository.currentSongData.collectLatest { songWithPosition ->
                songWithPosition?.let{
                    _serverUpdates.update { state ->
                        state.copy(
                            currentSong = SongInfo(
                                id = it.id,
                                artist = it.artist,
                                album = it.album,
                                title = it.title,
                                length = it.length
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeNetworkMessages() {
        observeJob?.cancel()
        scope.launch {
            networkManager.serverMessages.collect{ message ->
                when(message) {
                    // Server Event messages.
                    is EventType.ClosedPlaylistWithId -> { playlistRepository.serverClosedPlaylist(message.id) }
                    is EventType.FavouritePlaylist -> playlistRepository.serverFavourite(id =  message.id, isFavourite = message.favourite)
                    is EventType.GuiUpdates -> {
                        when (message.playlists) {
                            is EventType.MakeAllPlaylists -> {
                                playlistRepository.makeAllPlaylists(message.playlists.playlists)
                                playlistRepository.updateCurrentSong(message.activePlaylist, message.currentSong)
                            }
                        }
                        _serverUpdates.update {
                            ServerGuiValues(
                                activePlaylist =    message.activePlaylist,
                                currentPlaylist =   message.currentPlaylist,
                                currentSong =       _serverUpdates.value.currentSong,
                                currentSongId =     message.currentSong,
                                currentTime =       message.time,
                                playState =         when(message.playing){
                                                        "paused" ->     PlayState.PAUSED
                                                        "playing" ->    PlayState.PLAYING
                                                        else ->         PlayState.STOPPED
                                },
                                volume =            message.volume,
                            )
                        }
                    }
//                    is EventType.MakeCurrentPlaylist -> playlistRepository.makeCurrentPlaylist(message.playlist)
                    is EventType.MakePlaylist -> playlistRepository.makePlaylist(message.playlist)
                    // If playing, update current GUI settings to match server.
                    is EventType.Play -> _serverUpdates.update {
                        it.copy(
                            activePlaylist = message.activePlaylist,
                            currentSongId = message.row,
                            currentTime = message.time,
                            playState = PlayState.PLAYING
                        )
                    }
                    // If paused, sync time sent from server
                    is EventType.Pause -> _serverUpdates.update {
                        it.copy(
                            currentTime = message.time,
                            playState = PlayState.PAUSED
                        )
                    }
                    is EventType.RenamePlaylist -> playlistRepository.serverRenamedPlaylist(id = message.id, name = message.name)
                    is EventType.SeekTo -> _serverUpdates.update {it.copy(currentTime = message.time) }
                    is EventType.SongChanged -> _serverUpdates.update { it.copy(currentSongId = message.trackId) }
                    // When song is changed, update the player screen.
                    is EventType.SongInfo -> _serverUpdates.update {
                        it.copy(currentSong = SongInfo(
                            id = message.id,
                            url = message.url,
                            artist = message.artist,
                            album = message.album,
                            title = message.title,
                            length = message.length
                        ))
                    }
                    // If stopped, set player screen to defaults.
                    is EventType.Stop -> _serverUpdates.update {
                        val currentVolume = _serverUpdates.value.volume
                        it.copy(
                            currentSong = SongInfo(),
                            currentTime = 0,
                            volume = currentVolume,
                            playState = PlayState.STOPPED
                        )
                    }
                    is EventType.Time -> _serverUpdates.update { it.copy(currentTime = message.time) }
                    is EventType.VolumeChanged -> _serverUpdates.update {it.copy(volume = message.volume) }

                    // Server Error messages.
                    is ErrorType.CommandNotFound -> { serverErrorMessage("Command not found ${message.command}") }
                    is ErrorType.NotEnoughArguments -> { serverErrorMessage("Not enough arguments. Needs: ${message.required}") }
                    is ErrorType.PlaylistNotClosed -> { serverErrorMessage("Playlist not closed.") }
                    is ErrorType.PlaylistNotFound -> { serverErrorMessage("Playlist ${message.name} not found.") }
                    is ErrorType.WrongArgumentSent -> { serverErrorMessage("Wrong argument sent: ${message.argument}") }

                    // Server Response messages.
                    is ResponseType.ClearedPlaylist -> { serverResponseMessage("Cleared playlist ${message.name}.") }
                    is ResponseType.DeletedPlaylistWithId -> { serverResponseMessage("Deleted playlist with ID: ${message.id}.") }
                    is ResponseType.IsPlaylistAFavourite -> { serverResponseMessage("Is playlist ${message.id} a favourite? ${message.isFavourite}.") }
                    is ResponseType.PlaylistClosed -> { serverResponseMessage("Playlist ${message.id} closed.") }
                    is ResponseType.RemovedDuplicatesFromPlaylist -> { serverResponseMessage("Removed duplicates from playlist.") }
                    is ResponseType.RenamePlaylist -> { serverResponseMessage("Renamed playlist ${message.id} to ${message.name}.") }
                    is ResponseType.RemovedSongFromPlaylist -> { serverResponseMessage("Removed song from playlist ${message.name}.") }
                    is ResponseType.RunningCommand -> { serverResponseMessage("Running command ${message.command}.") }
                    is ResponseType.SentActivePlaylist -> { serverResponseMessage("Active playlist ID: ${message.id}.") }
                    is ResponseType.SendRequestedPlaylist -> { serverResponseMessage("Send requested playlist with ID: ${message.playlist.id}.") }
                    is ResponseType.SetActivePlaylistTo -> { serverResponseMessage("Set active playlist to ${message.id}.") }
                    is ResponseType.ShuffledPlaylist -> { serverResponseMessage("Shuffled playlist.") }
                    is ResponseType.ShuffledALlPlaylists -> { serverResponseMessage("Shuffled all playlists.") }
                    is ResponseType.Songs -> { serverResponseMessage("List of songs sent.") }

                    else -> Unit
                }
            }
        }
    }

    fun resetServerValues() {
        _serverUpdates.update { ServerGuiValues() }
    }

    fun updateInformation(updates: ServerGuiValues){
        _serverUpdates.value = updates
    }

    fun serverErrorMessage(error: String){
        Log.e("Server Error", error)
    }

    fun serverResponseMessage(message: String){
        Log.i("Server Response:", message)
    }
}