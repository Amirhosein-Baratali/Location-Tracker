package com.example.locationtracker.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd  HH:mm:ss", Locale.getDefault())
        val currentTime = Date()
        return dateFormat.format(currentTime)
    }
}