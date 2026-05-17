package com.example.androidcourse

import android.app.Application
import com.example.androidcourse.di.AppComponent
import com.example.androidcourse.di.DaggerAppComponent
import com.example.androidcourse.utils.CrashlyticsLogger
import com.example.androidcourse.utils.UserPreferences
import com.google.firebase.FirebaseApp

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        val userId = UserPreferences.getUserId(this)
        CrashlyticsLogger.setUserId(userId)

        appComponent = DaggerAppComponent.factory().create(this)
    }
}