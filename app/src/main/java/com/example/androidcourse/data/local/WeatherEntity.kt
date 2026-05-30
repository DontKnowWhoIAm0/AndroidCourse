package com.example.androidcourse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidcourse.utils.database.DbConstants

@Entity(tableName = DbConstants.WEATHER_TABLE)
data class WeatherEntity(
    @PrimaryKey val cityName: String,
    val temperature: Double,
    val description: String,
    val icon: String,
    val humidity: Int,
    val windSpeed: Double,
    val lastUpdated: Long,
    val requestedCity: String? = null
)