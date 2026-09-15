package com.example.strawberry_app.screens.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.strawberry_app.R
import com.example.strawberry_app.ui.theme.icons.arrow_left
import com.example.strawberry_app.ui.theme.icons.more_vert
import com.example.strawberry_app.ui.theme.icons.settings

@Composable
fun TopBar(
    heading: Int,
    onClick: () -> Unit,
    showMoreOptions: Boolean,
    modifier: Modifier = Modifier,
    onNavigationBack: (() -> Unit)? = null,
    showBackButton: Boolean = true
){
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(modifier = modifier
        .statusBarsPadding(),
        title = { Text(stringResource(heading)) },

        navigationIcon = {
            if (showBackButton && onNavigationBack != null) {
                IconButton(onClick = onNavigationBack) {
                    Icon(arrow_left, contentDescription = stringResource(R.string.back_button))
                }
            }
        },
        actions = {
            if (showMoreOptions){
                Box {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = more_vert,
                            contentDescription = stringResource(R.string.more_options)
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.settings_title)) },
                            trailingIcon = { Icon(imageVector = settings, contentDescription = stringResource(R.string.settings_title))},
                            onClick = {
                                menuExpanded = false
                                onClick()
                            }
                        )
                    }
                }
            }

        }
    )
}
