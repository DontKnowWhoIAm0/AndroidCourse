package com.example.androidcourse.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.domain.model.Weather
import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.launch

class WeatherDetailViewModel(
    val cityName: String,
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weather = mutableStateOf<Weather?>(null)
    val weather: State<Weather?> = _weather

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            val result = getWeatherUseCase(cityName)
            result.fold(
                onSuccess = { _weather.value = it.weather },
                onFailure = { _error.value = it.message }
            )
        }
    }
}