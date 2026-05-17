package com.example.androidcourse.di

import android.content.Context
import com.example.androidcourse.data.repository.WeatherStrings
import com.example.androidcourse.presentation.viewmodel.WeatherStringsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StringsModule {

    @Provides
    @Singleton
    fun provideWeatherStrings(context: Context): WeatherStrings {
        return WeatherStringsImpl(context)
    }
}