package com.example.strawberry_app.screens.repositories

import android.util.Log
import com.example.strawberry_app.music.SongInfo
import com.example.strawberry_app.network.ApplicationScope
import com.example.strawberry_app.network.ConnectionState
import com.example.strawberry_app.network.NetworkManager
import com.example.strawberry_app.network.protocol.ErrorType
import com.example.strawberry_app.network.protocol.EventType
import com.example.strawberry_app.network.protocol.ResponseType
import com.example.strawberry_app.screens.classes.PlayState
import com.example.strawberry_app.screens.classes.ServerGuiValues
import com.example.strawberry_app.screens.playerScreen.PlayerRepository
import com.example.strawberry_app.screens.playlistScreen.PlaylistRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val networkManager: NetworkManager,
    private val playlistRepository: PlaylistRepository,
    private val albumArtRepository: AlbumArtRepository,
    @param:ApplicationScope private val scope: CoroutineScope
){
    val serverUpdates = playerRepository.serverUpdates

    private var observeJob: Job? = null

    init {
        scope.launch {
            networkManager.connectionStateFlow.collect { state ->
                if (state is ConnectionState.Disconnected || state is ConnectionState.Connecting){
                    resetServerValues()
                    playlistRepository.deletePlaylists()
                }
                if (state is ConnectionState.Connected){
                    observeNetworkMessages()
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
                    is EventType.CoverImage -> {
                        albumArtRepository.receiveCover(name = message.name, coverImage = message.coverImage)
                        albumArtRepository.notifyAlbumArtReady(message.name)
                        albumArtRepository.removeRequestedArt(message.name)
                    }
                    is EventType.FavouritePlaylist -> playlistRepository.serverFavourite(id =  message.id, isFavourite = message.favourite)
                    is EventType.GuiUpdates -> {
                        playlistRepository.updateCurrentSong(
                            playlistId = message.activePlaylist,
                            songIndex = message.currentSong
                        )
                        playlistRepository.updatePlaylistState(
                            activePlaylist = message.activePlaylist,
                            currentPlaylist = message.currentPlaylist,
                            currentSongIndex = message.currentSong,
                            currentCoverImage = message.coverImage,
                            repeatMode = message.repeatMode,
                            shuffleMode = message.shuffleMode
                        )
                        playlistRepository.getAlbumArtFile(coverArt = message.coverImage)

                        // Only rebuild playlists when that payload is present
                        when (message.playlists) {
                            is EventType.MakeAllPlaylists -> {
                                playlistRepository.makeAllPlaylists(message.playlists.playlists)
                            }
                        }

                        playerRepository.getGuiUpdates(
                            ServerGuiValues(
                                activePlaylist = message.activePlaylist,
                                coverImage = message.coverImage,
                                currentPlaylist = message.currentPlaylist,
                                currentSong = serverUpdates.value.currentSong,
                                currentSongId = message.currentSong,
                                currentTime = message.time,
                                playState = when (message.playing) {
                                    "paused" -> PlayState.PAUSED
                                    "playing" -> PlayState.PLAYING
                                    else -> PlayState.STOPPED
                                },
                                repeatMode = message.repeatMode,
                                shuffleMode = message.shuffleMode,
                                volume = message.volume
                            )
                        )
                    }
                    is EventType.MakePlaylist -> playlistRepository.makePlaylist(message.playlist)
                    // If playing, update current GUI settings to match server.
                    is EventType.Play -> {
                        playerRepository.getGuiUpdates(
                            serverUpdates.value.copy(
                                activePlaylist = message.activePlaylist,
                                currentSongId = message.row,
                                currentTime = message.time,
                                playState = PlayState.PLAYING
                            )
                        )
                        playlistRepository.updateCurrentSong(
                            playlistId = message.activePlaylist,
                            songIndex = message.row
                        )
                    }
                    // If paused, sync time sent from server
                    is EventType.Pause -> playerRepository.getGuiUpdates(
                        serverUpdates.value.copy(
                            currentTime = message.time,
                            playState = PlayState.PAUSED
                        )
                    )
                    is EventType.RenamePlaylist -> playlistRepository.serverRenamedPlaylist(id = message.id, name = message.name)
                    is EventType.RepeatMode -> playlistRepository.updateRepeatMode(message.repeatMode)
                    is EventType.SeekTo -> playerRepository.getGuiUpdates(serverUpdates.value.copy(currentTime = message.time) )
                    is EventType.ShuffleMode -> playlistRepository.updateShuffleMode(message.shuffleMode)
                    is EventType.SongChanged -> {
                        playerRepository.getGuiUpdates(serverUpdates.value.copy(currentSongId = message.row))
                    }
                    // When song is changed, update the player screen.
                    is EventType.SongInfo ->{
                        playerRepository.getGuiUpdates(
                            serverUpdates.value.copy(
                                currentSong = SongInfo(
                                    album = message.album,
                                    artist = message.artist,
                                    coverImage = message.coverImage,
                                    id = message.id,
                                    length = message.length,
                                    playlistId = message.playlistId,
                                    title = message.title,
                                    url = message.url
                                )))
                        albumArtRepository.checkAlbumArt(name = message.coverImage)
                    }

                    // If stopped, set player screen to defaults.
                    is EventType.Stop -> {
                        val currentVolume = serverUpdates.value.volume
                        playerRepository.getGuiUpdates(
                                serverUpdates.value.copy(
                                        currentSong = SongInfo(),
                                        currentTime = 0,
                                        volume = currentVolume,
                                        playState = PlayState.STOPPED,
                                    )
                        )
                        albumArtRepository.checkAlbumArt(name = "")
                    }
                    is EventType.Time -> playerRepository.getGuiUpdates(serverUpdates.value.copy(currentTime = message.time))
                    is EventType.VolumeChanged -> playerRepository.getGuiUpdates(serverUpdates.value.copy(volume = message.volume))

                    // Server Error messages.
                    is ErrorType.CommandNotFound -> { serverErrorMessage("Command not found ${message.command}") }
                    is ErrorType.NotEnoughArguments -> { serverErrorMessage("Not enough arguments. Needs: ${message.required}") }
                    is ErrorType.PlaylistNotClosed -> { serverErrorMessage("Playlist not closed.") }
                    is ErrorType.PlaylistNotFound -> { serverErrorMessage("Playlist ${message.name} not found.") }
                    is ErrorType.WrongArgumentSent -> { serverErrorMessage("Wrong argument sent: ${message.argument}") }

                    // Server Response messages.
                    is ResponseType.ClearedPlaylist -> { serverResponseMessage("Cleared playlist ${message.name}.") }
                    is ResponseType.CurrentSong -> {
                        println("messagerepo current song called with playlist: ${message.playlistId} and song: ${message.songIndex}")
                        playlistRepository.updateCurrentSong(message.playlistId, message.songIndex)}
                    is ResponseType.DeletedPlaylistWithId -> { serverResponseMessage("Deleted playlist with ID: ${message.id}.") }
                    is ResponseType.IsPlaylistAFavourite -> { serverResponseMessage("Is playlist ${message.id} a favourite? ${message.isFavourite}.") }
                    is ResponseType.PlaylistChanged -> { serverResponseMessage("Playlist changed") }
                    is ResponseType.PlaylistClosed -> { serverResponseMessage("Playlist ${message.id} closed.") }
                    is ResponseType.RemovedDuplicatesFromPlaylist -> { serverResponseMessage("Removed duplicates from playlist.") }
                    is ResponseType.RemoveUnavailableSongs -> { serverResponseMessage("removed_unavailable_songs") }
                    is ResponseType.RenamePlaylist -> { serverResponseMessage("Renamed playlist ${message.id} to ${message.name}.") }
                    is ResponseType.RemovedSongFromPlaylist -> { serverResponseMessage("Removed songs from playlist ${message.id}.") }
                    is ResponseType.RepeatMode -> { serverResponseMessage("Repeat mode changed to ${message.repeatMode}") }
                    is ResponseType.RunningCommand -> { serverResponseMessage("Running command ${message.command}.") }
                    is ResponseType.SentActivePlaylist -> { serverResponseMessage("Active playlist ID: ${message.id}.") }
                    is ResponseType.SentAlbumCover -> {serverResponseMessage("Sent album cover")}
                    is ResponseType.SendRequestedPlaylist -> { serverResponseMessage("Send requested playlist with ID: ${message.playlist.id}.") }
                    is ResponseType.SetActivePlaylistTo -> { serverResponseMessage("Set active playlist to ${message.id}.") }
                    is ResponseType.ShuffledPlaylist -> { serverResponseMessage("Shuffle playlist mode: ${message.mode}.") }
                    is ResponseType.Songs -> { serverResponseMessage("List of songs sent.") }

                    else -> Unit
                }
            }
        }
    }

    fun resetServerValues() {
        playerRepository.getGuiUpdates(ServerGuiValues())
        albumArtRepository.notifyAlbumArtReady("")
    }

    fun serverErrorMessage(error: String){
        Log.e("Server Error", error)
    }

    fun serverResponseMessage(message: String){
        Log.i("Server Response:", message)
    }
}