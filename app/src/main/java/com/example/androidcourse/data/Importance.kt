package com.example.androidcourse.data

import android.app.NotificationManager
import com.example.androidcourse.R

enum class Importance(private val displayNameId: Int) {
    LOW(R.string.low_imp),
    MEDIUM(R.string.medium_imp),
    HIGH(R.string.high_imp),
    MAX(R.string.max_imp);

    fun getDisplayNameId(): Int {
        return displayNameId
    }

    fun getCompatPriority(): Int {
        return when (this) {
            LOW -> NotificationManager.IMPORTANCE_MIN
            MEDIUM -> NotificationManager.IMPORTANCE_LOW
            HIGH -> NotificationManager.IMPORTANCE_DEFAULT
            MAX -> NotificationManager.IMPORTANCE_HIGH
        }
    }
}