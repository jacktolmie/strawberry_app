package com.example.strawberry_app.screens.playlistScreen.composables

import com.example.strawberry_app.data.dao.SongWithPosition
import com.example.strawberry_app.data.entity.PlaylistEntity

fun samplePlaylists() = listOf(
    PlaylistEntity(id = 1L, name = "Rock Classics", favourite = false, playlistLength = 1000L, playlistSize = 2L),
    PlaylistEntity(id = 2L, name = "Jazz Favourites", favourite = true, playlistLength = 1000L, playlistSize = 2L),
    PlaylistEntity(id = 3L, name = "Pop Hits", favourite = false, playlistLength = 1000L, playlistSize = 2L)
)

fun sampleSongList() = listOf(
    SongWithPosition(
        artist = "Led Zeppelin",
        album = "Led Zeppelin IV",
        coverImage = "",
        id = 1L,
        length = 331000L,
        title = "Stairway to Heaven",
        url = "file:///music/stairway.mp3",
        position = 0L
    ),
    SongWithPosition(
        artist = "Pink Floyd",
        album = "The Dark Side of the Moon",
        coverImage = "",
        id = 2L,
        length = 543000L,
        title = "Time",
        url = "file:///music/time.mp3",
        position = 1L
    ),
    SongWithPosition(
        artist = "The Beatles",
        album = "Abbey Road",
        coverImage = "",
        id = 3L,
        length = 259000L,
        title = "Come Together",
        url = "file:///music/cometogether.mp3",
        position = 2L
    ),
    SongWithPosition(
        artist = "David Bowie",
        album = "The Rise and Fall of Ziggy Stardust",
        coverImage = "",
        id = 4L,
        length = 214000L,
        title = "Starman",
        url = "file:///music/starman.mp3",
        position = 3L
    ),
    SongWithPosition(
        artist = "Queen",
        album = "A Night at the Opera",
        coverImage = "",
        id = 5L,
        length = 354000L,
        title = "Bohemian Rhapsody",
        url = "file:///music/bohemian.mp3",
        position = 4L
    ),
    SongWithPosition(
        artist = "Fleetwood Mac",
        album = "Rumours",
        coverImage = "",
        id = 6L,
        length = 295000L,
        title = "Go Your Own Way",
        url = "file:///music/goyourownway.mp3",
        position = 5L
    ),
    SongWithPosition(
        artist = "The Rolling Stones",
        album = "Exile on Main St.",
        coverImage = "",
        id = 7L,
        length = 226000L,
        title = "Tumbling Dice",
        url = "file:///music/tumblingdice.mp3",
        position = 6L
    ),
    SongWithPosition(
        artist = "Jimi Hendrix",
        album = "Are You Experienced",
        coverImage = "",
        id = 8L,
        length = 242000L,
        title = "Purple Haze",
        url = "file:///music/purplehaze.mp3",
        position = 7L
    )
)