package com.example.locationtracker.presentation.location.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.locationtracker.domain.model.LatLong
import com.example.locationtracker.presentation.ui.theme.LocationTrackerTheme

@Composable
fun LocationList(modifier: Modifier = Modifier, latLongs: List<LatLong>?) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        latLongs?.forEachIndexed { index, location ->
            LocationItem(latLong = location, index == 0)
        }
    }
}

@Preview
@Composable
fun LocationListPreview() {
    LocationTrackerTheme {
        LocationList(
            latLongs = listOf(
                LatLong(35.23422359872653, 51.3250972143, "14:23:20"),
                LatLong(12.244145, 99.3250972143, "14:23:21")
            )
        )
    }
}
