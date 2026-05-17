package com.example.androidcourse.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.repository.WeatherStrings
import com.example.androidcourse.utils.WeatherResult
import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val strings: WeatherStrings
) : ViewModel() {

    private val _state = mutableStateOf<WeatherState>(WeatherState.Idle)
    val state: State<WeatherState> = _state

    fun fetchWeather(city: String) {
        val cleanCity = city.trim().replace(Regex("[\\n\\t]"), "").lowercase()
        if (cleanCity.isEmpty()) {
            _state.value = WeatherState.Error(strings.enterCity)
            return
        }

        viewModelScope.launch {
            _state.value = WeatherState.Loading
            val result = getWeatherUseCase(cleanCity)
            _state.value = result.fold(
                onSuccess = { WeatherState.Success(it) },
                onFailure = { WeatherState.Error(it.message ?: strings.unknown) }
            )
        }
    }
}

sealed class WeatherState {
    object Idle : WeatherState()
    object Loading : WeatherState()
    data class Success(val result: WeatherResult) : WeatherState()
    data class Error(val message: String) : WeatherState()
}