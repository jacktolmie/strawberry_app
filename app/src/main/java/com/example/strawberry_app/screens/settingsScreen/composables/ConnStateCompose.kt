package com.example.strawberry_app.screens.settingsScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.strawberry_app.R
import com.example.strawberry_app.network.ConnectionColour.GREEN
import com.example.strawberry_app.network.ConnectionColour.RED
import com.example.strawberry_app.network.ConnectionColour.YELLOW
import com.example.strawberry_app.network.SettingsGuiData
import com.example.strawberry_app.screens.TextBox

@Composable
fun ConnStateMedLrg(
    settingsGuiData: SettingsGuiData,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    )
    {
        TextBox(R.string.settings_connection_status, MaterialTheme.typography.bodyLarge)

        Spacer(modifier = modifier.width(5.dp))

        Icon(
            modifier = modifier.size(18.dp),
            painter = painterResource(R.drawable.circle_24dp_e3e3e3_fill1_wght400_grad0_opsz24),
            contentDescription = null,
            tint = when(settingsGuiData.connectionColour){
                RED -> Color.Red
                GREEN -> Color.Green
                YELLOW -> Color.Yellow
            }
        )

        Spacer(modifier = modifier.width(5.dp))

        TextBox(
            text = settingsGuiData.connectionState,
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ConnStateSmall(
    settingsGuiData: SettingsGuiData,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        TextBox(R.string.settings_connection_status, MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.width(5.dp))

        Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(R.drawable.circle_24dp_e3e3e3_fill1_wght400_grad0_opsz24),
            contentDescription = null,
            tint = when(settingsGuiData.connectionColour){
                RED -> Color.Red
                GREEN -> Color.Green
                YELLOW -> Color.Yellow
            }
        )

        Spacer(modifier = Modifier.width(5.dp))

        TextBox(
            text = settingsGuiData.connectionState,
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}