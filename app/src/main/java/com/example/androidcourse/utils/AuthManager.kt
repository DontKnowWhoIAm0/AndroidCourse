package com.example.androidcourse.utils

import android.content.Context
import android.content.SharedPreferences

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(Сonstants.PREFS_NAME, Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.getBoolean(Сonstants.KEY_IS_LOGGED_IN, false)
    fun getUserEmail(): String? = prefs.getString(Сonstants.KEY_USER_EMAIL, null)
    fun getUserId(): Int = prefs.getInt(Сonstants.KEY_USER_ID, -1)

    fun saveSession(email: String, userId: Int) {
        prefs.edit().apply {
            putBoolean(Сonstants.KEY_IS_LOGGED_IN, true)
            putString(Сonstants.KEY_USER_EMAIL, email)
            putInt(Сonstants.KEY_USER_ID, userId)
        }.apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}