package com.example.androidcourse.service

enum class PushNotificationKind(val value: String) {
    PROMO("promo"),
    AUTH("auth"),
    WEATHER_ALERT("weather_alert"),
    UNKNOWN("unknown");

    companion object {
        fun fromValue(value: String?): PushNotificationKind =
            entries.firstOrNull { it.value == value } ?: UNKNOWN
    }
}