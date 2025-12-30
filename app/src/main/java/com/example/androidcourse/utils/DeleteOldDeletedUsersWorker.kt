package com.example.androidcourse.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.UserRepository

class DeleteOldDeletedUsersWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getDatabase(applicationContext)
        val repository = UserRepository(db.userDao())

        repository.deleteOldDeletedUsers()
        return Result.success()
    }
}