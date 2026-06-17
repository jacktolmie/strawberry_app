package com.example.strawberry_app.screens.playerScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputEventHandler
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
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
fun CreateLongPressButton(image: ImageVector, description: String, control: () -> Unit){
    Box( modifier = Modifier
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val down = awaitPointerEvent()
                    if (down.type == PointerEventType.Press) {
                        val job = this.launch {
                            while (true) {
                                control()
                                delay(1000)
                            }
                        }
                        awaitPointerEvent()
                        job.cancel()
                    }
                }
            }

    ) {

    }
}