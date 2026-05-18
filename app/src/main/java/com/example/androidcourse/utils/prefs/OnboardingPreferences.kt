package com.example.androidcourse.utils.prefs

import android.content.Context

object OnboardingPreferences {

    private const val PREFS_NAME = "onboarding_prefs"
    private const val KEY_ONBOARDING_SHOWN = "onboarding_shown"

    fun isOnboardingShown(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_ONBOARDING_SHOWN, false)
    }

    fun markOnboardingShown(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_ONBOARDING_SHOWN, true)
            .apply()
    }
}