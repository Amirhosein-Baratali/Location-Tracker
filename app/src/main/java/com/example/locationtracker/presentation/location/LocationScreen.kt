package com.example.locationtracker.presentation.location

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.locationtracker.presentation.location.components.LocationList
import com.example.locationtracker.presentation.location.components.ToggleButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen() {

    val viewModel: LocationViewModel = viewModel()
    val locationData by viewModel.locations.collectAsState()
    var started by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(modifier = Modifier.padding(end = 20.dp)) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Location Tracker",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    ToggleButton(
                        started = started,
                        onClick = {
                            viewModel.triggerLocationService(!started)
                            started = !started
                        }
                    )
                }
            })
        }
    ) { paddingValues ->
        LocationList(modifier = Modifier.padding(paddingValues), latLongs = locationData.reversed())
    }
}