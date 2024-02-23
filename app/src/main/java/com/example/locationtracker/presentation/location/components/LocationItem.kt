package com.example.locationtracker.presentation.location.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.locationtracker.domain.model.LatLong
import com.example.locationtracker.presentation.ui.theme.LocationTrackerTheme
import java.text.DecimalFormat

@Composable
fun LocationItem(latLong: LatLong, isLastLocation: Boolean = false) {
    val decimalFormat = DecimalFormat("#.#####")
    val lat = decimalFormat.format(latLong.latitude)
    val lng = decimalFormat.format(latLong.longitude)


    val textStyle =
        if (isLastLocation)
            MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.ExtraBold)
        else MaterialTheme.typography.bodyMedium

    Column(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color.Cyan, shape = MaterialTheme.shapes.large)
            .padding(8.dp)
    ) {
        Text(text = "Latitude: $lat", style = textStyle)
        Text(text = "Longitude: $lng", style = textStyle)
        Text(
            modifier = Modifier.align(Alignment.End),
            text = latLong.timeStamp,
            style = textStyle.copy(color = Color.DarkGray)
        )
    }

}

@Preview
@Composable
fun LocationItemPreview() {
    LocationTrackerTheme {
        LocationItem(
            latLong = LatLong(
                32.63452,
                54.325234,
                "1402/12/02 12:45:21"
            ),
            isLastLocation = true
        )
    }
}
