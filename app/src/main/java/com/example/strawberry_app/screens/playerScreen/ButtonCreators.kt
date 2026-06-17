package com.example.strawberry_app.screens.playerScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun CreateButton(image: ImageVector, description: String, control: () -> Unit) {
    IconButton(
        modifier = Modifier.size(48.dp),
        onClick = { control() }
    ) {
        Icon(
            imageVector = image,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CreateLongPressButton(image: ImageVector, description: String, shortPress: () -> Unit, longPress: () -> Unit) {
    Box(modifier = Modifier
        .combinedClickable(
            enabled = true,
            onClick = { shortPress() },
            onLongClick = { longPress()}
        )
    ){
        Icon(imageVector = image, contentDescription = description)
    }
}

@Composable
fun CreateRepeatButton(image: ImageVector, description: String, control: () -> Unit){
    Box( modifier = Modifier
        .clickable { control() }
        .onTouchHeld { control() }
    ) {
        Icon(imageVector = image, contentDescription = description)
    }
}

fun Modifier.onTouchHeld(
    pollDelay: Long = 1000L,
    onTouchHeld: () -> Unit
) = composed {
    val scope = rememberCoroutineScope()
    pointerInput(onTouchHeld) {
        awaitEachGesture {
            val initialDown = awaitFirstDown(requireUnconsumed = false)
            val job = scope.launch {
                while (initialDown.pressed) {
                    onTouchHeld()
                    delay(pollDelay.milliseconds)
                }
            }
            waitForUpOrCancellation()
            job.cancel()
        }
    }
}