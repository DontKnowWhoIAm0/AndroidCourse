package com.example.androidcourse.utils

import com.example.androidcourse.domain.model.Weather

data class WeatherResult(
    val weather: Weather,
    val source: DataSource
)