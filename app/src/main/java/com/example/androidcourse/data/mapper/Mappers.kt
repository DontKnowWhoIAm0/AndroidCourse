package com.example.androidcourse.data.mapper

import com.example.androidcourse.data.local.WeatherEntity
import com.example.androidcourse.data.network.WeatherResponseDto
import com.example.androidcourse.domain.model.Weather

fun WeatherResponseDto.toDomain(): Weather = Weather(
    cityName = name,
    temperature = main.temp,
    description = weather.firstOrNull()?.description ?: "",
    icon = weather.firstOrNull()?.icon ?: "",
    humidity = main.humidity,
    windSpeed = wind.speed
)

fun Weather.toEntity(lastUpdated: Long, requestedCity: String? = null) = WeatherEntity(
    cityName = cityName.lowercase(),
    temperature = temperature,
    description = description,
    icon = icon,
    humidity = humidity,
    windSpeed = windSpeed,
    lastUpdated = lastUpdated,
    requestedCity = requestedCity
)

fun WeatherEntity.toDomain(): Weather = Weather(
    cityName = cityName,
    temperature = temperature,
    description = description,
    icon = icon,
    humidity = humidity,
    windSpeed = windSpeed
)