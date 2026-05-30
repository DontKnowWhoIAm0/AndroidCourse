package com.example.androidcourse

import android.app.Application
import com.example.androidcourse.di.app.AppComponent
import com.example.androidcourse.di.app.DaggerAppComponent
import com.example.androidcourse.service.NotificationHelper
import com.example.androidcourse.utils.analytics.CrashlyticsLogger
import com.example.androidcourse.utils.prefs.UserPreferences
import com.google.firebase.FirebaseApp

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        val userId = UserPreferences.getUserId(this)
        CrashlyticsLogger.setUserId(userId)

        NotificationHelper.createNotificationChannels(this)

        appComponent = DaggerAppComponent.factory().create(this)
    }
}