package com.example.locationtracker.presentation.location.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToggleButton(started: Boolean, onClick: () -> Unit) {
    val buttonColor = if (started) Color.Red else Color.Green
    val iconSize = 48.dp
    val icon = if (started) Icons.Default.StopCircle else Icons.Default.PlayCircleFilled

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(36.dp)
            .padding(4.dp)
            .border(width = 1.dp, color = buttonColor, shape = CircleShape),
        content = {
            Icon(
                imageVector = icon,
                contentDescription = "Start-Stop Button",
                tint = buttonColor,
                modifier = Modifier.size(iconSize)
            )
        }
    )
}