package com.example.androidcourse.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.repository.WeatherStrings
import com.example.androidcourse.utils.WeatherResult
import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import com.example.androidcourse.domain.usecase.ValidateCityUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val validateCityUseCase: ValidateCityUseCase,
    private val strings: WeatherStrings
) : ViewModel() {

    private val _state = mutableStateOf<WeatherState>(WeatherState.Idle)
    val state: State<WeatherState> = _state

    fun fetchWeather(city: String) {
        val cleanCity = validateCityUseCase(city)
        if (cleanCity == null) {
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