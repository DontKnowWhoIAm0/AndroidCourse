package com.example.androidcourse.data.network

import com.example.androidcourse.BuildConfig
import com.example.androidcourse.utils.ApiParams
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(ApiParams.WEATHER)
    suspend fun getWeather(
        @Query(ApiParams.CITY) city: String,
        @Query(ApiParams.API_KEY) apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query(ApiParams.UNITS) units: String = ApiParams.UNITS_METRIC
    ): WeatherResponseDto
}