package com.example.androidcourse.domain.usecase

import com.example.androidcourse.utils.WeatherResult
import com.example.androidcourse.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): Result<WeatherResult> {
        return repository.getWeather(city)
    }
}