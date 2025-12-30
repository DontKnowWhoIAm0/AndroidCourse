package com.example.androidcourse

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import com.example.androidcourse.utils.DeleteOldDeletedUsersWorker

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val workRequest = PeriodicWorkRequestBuilder<DeleteOldDeletedUsersWorker>(1, TimeUnit.DAYS).build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork("delete_expired_users", ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }
}