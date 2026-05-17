package com.example.androidcourse.domain.model

data class Weather(
    val cityName: String,
    val temperature: Double,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double
)