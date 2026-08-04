<div align="center">

# Strawberry Player Remote App

</div>

This app is a remote controller for the [Strawberry Music Player](https://www.strawberrymusicplayer.org/ "Strawberry Music Player site")

It will not work without the code I added to the fork I created for it. I will be submitting the code to have it reviewed to see if it can be added to the Strawberry player that is available from the source above.

Currently it has the following functionality:

---

<div align="center">

## Player Screen

<img src="/images/player.png" width="45%"/> <img src="/images/player_with_song.png" width="45%"/>

</div>

- Title of song, with band and album below
- Cover image if available
- Volume control:
  - Up button
  - Volume slider
  - Down button
  - Mute
- Seek bar for song progress
- Current playing time, and total song time
- Player controls:
  - Restart or previous song
  - Seek back (short or long press available)
  - Play/Pause/Stop
  - Seek forward (short or long press available)
  - Next song

---

<div align="center">

## Playlists

<img src="/images/playlist.png" width="45%"/> <img src="/images/playlist_options.png" width="45%"/>

</div>

- Playlists with names
- Length of playlist time and number of songs
- Options for each playlist:
  - Change favourite status
  - Repeat options match (overall) Strawberry's options
  - Shuffle current or all playlists
  - Clear playlist
  - Delete playlist
  - Remove duplicate songs
  - Remove unavailable songs
  - Rename playlist
- Move single song to new row (long press to activate, use drag icon to move)
- Delete songs from playlist (short click each song, select delete icon)

---

<div align="center">

## Settings

<img src="/images/settings.png" width="45%"/>

</div>

- Set IP address
- Set port number
- Password if wanted — must match server side under settings