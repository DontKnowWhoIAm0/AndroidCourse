package com.example.androidcourse.data.mapper

import androidx.room.TypeConverter
import java.util.Date

object TypeConverters {

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromLongToDate(timeInMillis: Long?): Date? {
        return timeInMillis?.let { Date(it) }
    }

}