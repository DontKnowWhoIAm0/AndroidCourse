package com.example.androidcourse.utils

import android.content.Context
import android.content.SharedPreferences

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)
    fun getUserEmail(): String? = prefs.getString("user_email", null)
    fun getUserId(): Int = prefs.getInt("user_id", -1)

    fun saveSession(email: String, userId: Int) {
        prefs.edit().apply {
            putBoolean("is_logged_in", true)
            putString("user_email", email)
            putInt("user_id", userId)
        }.apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}