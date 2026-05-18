package com.example.androidcourse.utils.analytics

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object AnalyticsLogger {

    private val analytics: FirebaseAnalytics by lazy { Firebase.analytics }

    fun logOnboardingShown() {
        Log.d("ANALYTICS", "logOnboardingShown called")
        analytics.logEvent("onboarding_shown", Bundle().apply {
            putString("screen", "onboarding")
        })
    }

    fun logOnboardingDismissed() {
        Log.d("ANALYTICS", "logOnboardingDismissed called")
        analytics.logEvent("onboarding_dismissed", Bundle().apply {
            putString("screen", "onboarding")
        })
    }

    fun logScreenView(screenName: String) {
        Log.d("ANALYTICS", "ScreenView: $screenName")
        analytics.logEvent("screen_opened", Bundle().apply {
            putString("screen_name", screenName)
        })
    }
}