package com.example.locationtracker.util

import android.app.ActivityManager
import android.content.Context

fun Context.isAppInBackground(): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    val appProcesses = activityManager?.runningAppProcesses ?: return false
    for (appProcess in appProcesses) {
        if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
            appProcess.processName == packageName
        ) {
            return false
        }
    }
    return true
}