package com.example.androidcourse.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object AnalyticsLogger {

    private val analytics: FirebaseAnalytics by lazy { Firebase.analytics }

    fun logOnboardingShown() {
        android.util.Log.d("ANALYTICS", "logOnboardingShown called")
        analytics.logEvent("onboarding_shown", Bundle().apply {
            putString("screen", "onboarding")
        })
    }

    fun logOnboardingDismissed() {
        android.util.Log.d("ANALYTICS", "logOnboardingDismissed called")
        analytics.logEvent("onboarding_dismissed", Bundle().apply {
            putString("screen", "onboarding")
        })
    }
}