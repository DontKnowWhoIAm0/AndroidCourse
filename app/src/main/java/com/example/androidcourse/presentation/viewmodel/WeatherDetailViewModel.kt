    package com.example.androidcourse.presentation.viewmodel

    import androidx.compose.runtime.mutableStateOf
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.androidcourse.di.weather_details.CityName
    import com.example.androidcourse.domain.model.Weather
    import com.example.androidcourse.domain.usecase.GetWeatherUseCase
    import kotlinx.coroutines.launch
    import androidx.compose.runtime.State
    import com.example.androidcourse.data.repository.WeatherStrings
    import javax.inject.Inject

    class WeatherDetailViewModel @Inject constructor(
        @CityName private val cityName: String,
        private val getWeatherUseCase: GetWeatherUseCase,
        private val strings: WeatherStrings
    ) : ViewModel() {

        private val _weather = mutableStateOf<Weather?>(null)
        val weather: State<Weather?> = _weather

        private val _error = mutableStateOf<String?>(null)
        val error: State<String?> = _error

        init {
            fetchDetail()
        }

        private fun fetchDetail() {
            viewModelScope.launch {
                val result = getWeatherUseCase(cityName)
                result.fold(
                    onSuccess = { _weather.value = it.weather },
                    onFailure = { _error.value = it.message ?: strings.unknown }
                )
            }
        }
    }