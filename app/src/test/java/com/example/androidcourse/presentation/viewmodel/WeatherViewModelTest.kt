package com.example.androidcourse.presentation.viewmodel

import com.example.androidcourse.data.repository.WeatherStrings
import com.example.androidcourse.domain.model.Weather
import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import com.example.androidcourse.domain.usecase.ValidateCityUseCase
import com.example.androidcourse.utils.MainCoroutineRule
import com.example.androidcourse.utils.WeatherResult
import com.example.androidcourse.utils.database.DataSource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.DefaultAsserter.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals

class WeatherViewModelTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getWeatherUseCase: GetWeatherUseCase = mockk()
    private val validateCityUseCase = ValidateCityUseCase()
    private val strings: WeatherStrings = mockk {
        every { enterCity } returns "Enter city name"
        every { unknown } returns "Unknown error"
    }

    private val fakeWeather = Weather(
        cityName = "london",
        temperature = 15.0,
        description = "cloudy",
        icon = "04d",
        humidity = 80,
        windSpeed = 5.0
    )

    @Test
    fun `fetchWeather updates state to Success when use case returns data`() = runTest(coroutineRule.testDispatcher) {
        val viewModel = WeatherViewModel(getWeatherUseCase, validateCityUseCase, strings)
        val expectedResult = WeatherResult(fakeWeather, DataSource.REMOTE)

        coEvery { getWeatherUseCase("london") } returns Result.success(expectedResult)

        viewModel.fetchWeather("london")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue("Expected Success but was $state", state is WeatherState.Success)
        assertEquals(expectedResult, (state as WeatherState.Success).result)
    }

    @Test
    fun `fetchWeather updates state to Error when use case fails`() = runTest(coroutineRule.testDispatcher) {
        val viewModel = WeatherViewModel(getWeatherUseCase, validateCityUseCase, strings)
        val errorMessage = "No internet connection"

        coEvery { getWeatherUseCase("london") } returns Result.failure(Exception(errorMessage))

        viewModel.fetchWeather("london")
        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue("Expected Error but was $state", state is WeatherState.Error)
        assertEquals(errorMessage, (state as WeatherState.Error).message)
    }
}