package com.example.locationtracker.presentation.location_service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import com.example.locationtracker.R
import com.example.locationtracker.presentation.notification.LocationNotificationService
import com.example.locationtracker.util.isAppInBackground

class LocationService : Service() {

    private lateinit var locationManager: LocationManager
    private lateinit var notificationService: LocationNotificationService
    private var previousLocation: Location? = null

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            broadcastCurrentLocation(location)
            if (isAppInBackground()) showNotification(location)
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    }

    override fun onCreate() {
        notificationService = LocationNotificationService(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Action.START.toString() -> requestLocationUpdates()
            Action.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun requestLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                LOCATION_UPDATE_INTERVAL,
                LOCATION_UPDATE_DISTANCE,
                locationListener
            )
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }
    }

    private fun showNotification(location: Location) {
        if (!locationChanged(location)) return

        previousLocation = location
        val title = getString(R.string.app_name)
        val content = getString(R.string.current_location, location.latitude, location.longitude)
        notificationService.showNotification(title, content)
    }

    private fun locationChanged(location: Location) = previousLocation == null
            || !previousLocation!!.latitude.equals(location.latitude)
            || !previousLocation!!.longitude.equals(location.longitude)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(locationListener)
    }

    private fun broadcastCurrentLocation(location: Location) {
        val intent = Intent(ACTION_BROADCAST_LOCATION).apply {
            putExtra(KEY_CURRENT_LOCATION_LATITUDE, location.latitude)
            putExtra(KEY_CURRENT_LOCATION_LONGITUDE, location.longitude)
        }
        sendBroadcast(intent)
    }

    companion object {
        private const val LOCATION_UPDATE_INTERVAL = 5000L
        private const val LOCATION_UPDATE_DISTANCE = 0f
        const val ACTION_BROADCAST_LOCATION = "com.example.locationtracker.currentLocation"
        const val KEY_CURRENT_LOCATION_LATITUDE = "current_location_latitude"
        const val KEY_CURRENT_LOCATION_LONGITUDE = "current_location_longitude"
    }

    enum class Action {
        START, STOP
    }
}
