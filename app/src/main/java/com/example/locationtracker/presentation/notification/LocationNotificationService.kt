package com.example.locationtracker.presentation.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.locationtracker.R

class LocationNotificationService(
    private val context: Context
) {
    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "channel_location"
        private const val NOTIFICATION_ID = 0
    }

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showNotification(title: String, content: String) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_location)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }
}