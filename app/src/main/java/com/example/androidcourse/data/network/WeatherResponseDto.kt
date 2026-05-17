package com.example.androidcourse.data.network

data class WeatherResponseDto(
    val name: String,
    val main: Main,
    val weather: List<WeatherDto>,
    val wind: Wind
)

data class Main(val temp: Double, val humidity: Int)
data class WeatherDto(val description: String, val icon: String)
data class Wind(val speed: Double)