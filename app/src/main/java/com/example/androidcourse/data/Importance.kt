package com.example.androidcourse.data

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import com.example.androidcourse.R

enum class Importance(private val displayNameId: Int) {
    LOW(R.string.low_imp),
    MEDIUM(R.string.medium_imp),
    HIGH(R.string.high_imp),
    URGENT(R.string.urgent_imp);

    fun getDisplayNameId(): Int {
        return displayNameId
    }

    fun getPriority(): Int {
        return when (this) {
            LOW -> NotificationCompat.PRIORITY_MIN
            MEDIUM -> NotificationCompat.PRIORITY_LOW
            HIGH -> NotificationCompat.PRIORITY_DEFAULT
            URGENT -> NotificationCompat.PRIORITY_HIGH
        }
    }

    fun getImportance(): Int {
        return when (this) {
            LOW -> NotificationManager.IMPORTANCE_MIN
            MEDIUM -> NotificationManager.IMPORTANCE_LOW
            HIGH -> NotificationManager.IMPORTANCE_DEFAULT
            URGENT -> NotificationManager.IMPORTANCE_HIGH
        }
    }

    fun getChannelId(): String {
        return when (this) {
            LOW -> "channel_low"
            MEDIUM -> "channel_medium"
            HIGH -> "channel_high"
            URGENT -> "channel_max"
        }
    }
}