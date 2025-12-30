package com.example.androidcourse.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidcourse.R
import com.example.androidcourse.data.dao.UserDao
import com.example.androidcourse.data.dao.YarnDao
import com.example.androidcourse.data.entity.YarnEntity
import com.example.androidcourse.data.entity.UserEntity

@Database(entities = [YarnEntity::class, UserEntity::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun yarnDao(): YarnDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    context.getString(R.string.database_name)
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}