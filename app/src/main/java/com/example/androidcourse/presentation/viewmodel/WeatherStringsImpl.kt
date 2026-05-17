package com.example.androidcourse.presentation.viewmodel

import android.content.Context
import com.example.androidcourse.R
import com.example.androidcourse.data.repository.WeatherStrings

class WeatherStringsImpl(private val context: Context) : WeatherStrings {
    override val noInternet get() = context.getString(R.string.no_internet)
    override val cityNotFound get() = context.getString(R.string.city_not_found)
    override val authError get() = context.getString(R.string.auth_error)
    override fun serviceUnavailable(code: Int) = context.getString(R.string.service_unavailable, code)
    override val serviceTimeout get() = context.getString(R.string.service_timeout)
    override val unknownError get() = context.getString(R.string.unknown_error)

    override val enterCity get() = context.getString(R.string.enter_city)
    override val unknown get() = context.getString(R.string.unknown)
}