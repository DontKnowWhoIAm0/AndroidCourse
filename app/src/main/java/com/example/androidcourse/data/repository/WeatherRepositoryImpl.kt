package com.example.androidcourse.data.repository

import com.example.androidcourse.data.local.WeatherDao
import com.example.androidcourse.data.mapper.toDomain
import com.example.androidcourse.data.mapper.toEntity
import com.example.androidcourse.data.network.WeatherApi
import com.example.androidcourse.utils.database.DataSource
import com.example.androidcourse.utils.WeatherResult
import com.example.androidcourse.domain.repository.WeatherRepository
import java.net.UnknownHostException
import java.net.SocketTimeoutException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao,
    private val strings: WeatherStrings
) : WeatherRepository {

    private val cacheDuration = 60_000L

    override suspend fun getWeather(city: String): Result<WeatherResult> {
        val cached = dao.getWeather(city)
        val now = System.currentTimeMillis()
        if (cached != null && now - cached.lastUpdated < cacheDuration) {
            return Result.success(WeatherResult(cached.toDomain(), DataSource.CACHE))
        }
        return try {
            val response = api.getWeather(city)
            val weather = response.toDomain()
            dao.insert(weather.toEntity(now, city))

            Result.success(WeatherResult(weather, DataSource.REMOTE))
        } catch (e: Exception) {
            if (cached != null) {
                Result.success(WeatherResult(cached.toDomain(), DataSource.CACHE))
            } else {
                val message = when (e) {
                    is UnknownHostException -> strings.noInternet
                    is retrofit2.HttpException -> when(e.code()) {
                        404 -> strings.cityNotFound
                        401 -> strings.authError
                        else -> strings.serviceUnavailable(e.code())
                    }
                    is SocketTimeoutException -> strings.serviceTimeout
                    else -> strings.unknownError
                }
                Result.failure(Exception(message))
            }
        }
    }
}