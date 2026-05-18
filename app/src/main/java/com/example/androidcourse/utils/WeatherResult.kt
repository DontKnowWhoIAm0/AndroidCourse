package com.example.androidcourse.utils

import com.example.androidcourse.domain.model.Weather
import com.example.androidcourse.utils.database.DataSource

data class WeatherResult(
    val weather: Weather,
    val source: DataSource
)