package com.example.androidcourse.di

import android.content.Context
import androidx.room.Room
import com.example.androidcourse.data.local.WeatherDao
import com.example.androidcourse.data.local.WeatherDatabase
import com.example.androidcourse.utils.DbConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            DbConstants.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }
}