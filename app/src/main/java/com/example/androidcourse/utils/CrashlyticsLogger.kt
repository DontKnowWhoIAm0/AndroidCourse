package com.example.androidcourse.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsLogger {

    fun setUserId(userId: String) {
        FirebaseCrashlytics.getInstance().setUserId(userId)
    }

    fun logScreenView(screenName: String) {
        FirebaseCrashlytics.getInstance().log("Screen: $screenName")
    }

    fun logEvent(event: String) {
        FirebaseCrashlytics.getInstance().log(event)
    }

    fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    fun logScreenViewAnalytics(analytics: FirebaseAnalytics, screenName: String) {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        }
        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}