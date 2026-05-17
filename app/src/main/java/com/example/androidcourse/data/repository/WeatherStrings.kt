package com.example.androidcourse.data.repository

interface WeatherStrings {
    val noInternet: String
    val cityNotFound: String
    val authError: String
    fun serviceUnavailable(code: Int): String
    val serviceTimeout: String
    val unknownError: String

    val enterCity: String
    val unknown: String
}