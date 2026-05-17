package com.example.androidcourse.domain.repository

import com.example.androidcourse.utils.WeatherResult

interface WeatherRepository {
    suspend fun getWeather(city: String): Result<WeatherResult>
}