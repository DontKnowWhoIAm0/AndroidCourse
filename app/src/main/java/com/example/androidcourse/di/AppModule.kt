package com.example.androidcourse.di

import com.example.androidcourse.BuildConfig
import com.example.androidcourse.data.local.WeatherDao
import com.example.androidcourse.data.network.WeatherApi
import com.example.androidcourse.data.repository.WeatherRepositoryImpl
import com.example.androidcourse.data.repository.WeatherStrings
import com.example.androidcourse.domain.repository.WeatherRepository
import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.OPEN_WEATHER_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    fun provideWeatherRepository(api: WeatherApi, dao: WeatherDao, strings: WeatherStrings): WeatherRepository {
        return WeatherRepositoryImpl(api, dao, strings)
    }

    fun provideGetWeatherUseCase(repository: WeatherRepository) = GetWeatherUseCase(repository)
}