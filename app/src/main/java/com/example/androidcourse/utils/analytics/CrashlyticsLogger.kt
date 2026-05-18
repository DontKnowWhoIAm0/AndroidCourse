package com.example.androidcourse.utils.analytics

import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsLogger {

    fun setUserId(userId: String) {
        FirebaseCrashlytics.getInstance().setUserId(userId)
    }

    fun logEvent(event: String) {
        FirebaseCrashlytics.getInstance().log(event)
    }

    fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}