package com.example.strawberry_app.screens

fun formatTime(time: Long): String {
    val hours = time / 3_600_000
    val minutes = (time % 3_600_000) / 60_000
    val seconds = (time % 60_000) / 1_000

    return  if (hours > 0L) "%d:%02d:%02d".format(hours, minutes, seconds)
    else "%d:%02d".format(minutes, seconds)
}