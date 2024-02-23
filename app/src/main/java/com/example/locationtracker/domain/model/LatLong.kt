package com.example.locationtracker.domain.model

import android.location.Location

data class LatLong(
    val latitude: Double,
    val longitude: Double,
    val timeStamp: String
)

fun Location.toLatLong(timeStamp: String) = LatLong(latitude, longitude, timeStamp)
