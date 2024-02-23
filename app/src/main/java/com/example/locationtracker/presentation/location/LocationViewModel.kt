package com.example.locationtracker.presentation.location

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.locationtracker.domain.model.LatLong
import com.example.locationtracker.domain.model.toLatLong
import com.example.locationtracker.presentation.location_service.LocationService
import com.example.locationtracker.presentation.location_service.LocationService.Companion.ACTION_BROADCAST_LOCATION
import com.example.locationtracker.presentation.location_service.LocationService.Companion.KEY_CURRENT_LOCATION_LATITUDE
import com.example.locationtracker.presentation.location_service.LocationService.Companion.KEY_CURRENT_LOCATION_LONGITUDE
import com.example.locationtracker.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val application: Application,
) : AndroidViewModel(application) {

    private val _locations = MutableStateFlow<List<LatLong>>(emptyList())
    val locations: StateFlow<List<LatLong>> = _locations

    private val locationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                if (it.action == ACTION_BROADCAST_LOCATION) {
                    val latitude = it.getDoubleExtra(KEY_CURRENT_LOCATION_LATITUDE, 0.0)
                    val longitude = it.getDoubleExtra(KEY_CURRENT_LOCATION_LONGITUDE, 0.0)
                    val location = Location("").apply {
                        this.latitude = latitude
                        this.longitude = longitude
                    }
                    updateLocation(location)
                }
            }
        }
    }

    init {
        triggerLocationService()
        val intentFilter = IntentFilter(ACTION_BROADCAST_LOCATION)
        ContextCompat.registerReceiver(
            application,
            locationReceiver,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    private fun updateLocation(location: Location) {
        val latLong = location.toLatLong(DateUtil.getCurrentDateTime())
        val updatedLocations = _locations.value.toMutableList()
        updatedLocations.add(latLong)
        _locations.value = updatedLocations
    }

    fun triggerLocationService(start: Boolean = true) {
        Intent(application, LocationService::class.java).also { intent ->
            intent.action =
                if (start) LocationService.Action.START.toString()
                else LocationService.Action.STOP.toString()
            application.startService(intent)
        }
    }

    override fun onCleared() {
        application.unregisterReceiver(locationReceiver)
        super.onCleared()
    }
}
