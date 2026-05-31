package com.example.androidcourse.presentation.viewmodel

import com.example.androidcourse.data.repository.WeatherStrings
import com.example.androidcourse.domain.model.Weather
import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import com.example.androidcourse.utils.MainCoroutineRule
import com.example.androidcourse.utils.WeatherResult
import com.example.androidcourse.utils.database.DataSource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class WeatherDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val getWeatherUseCase: GetWeatherUseCase = mockk()
    private val strings: WeatherStrings = mockk {
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
    fun `fetchDetail sets weather state on success`() = runTest(coroutineRule.testDispatcher) {
        val expectedResult = WeatherResult(fakeWeather, DataSource.CACHE)
        coEvery { getWeatherUseCase("london") } returns Result.success(expectedResult)
        val viewModel = WeatherDetailViewModel("london", getWeatherUseCase, strings)
        advanceUntilIdle()

        assertNotNull(viewModel.weather.value)
        assertNull(viewModel.error.value)
        assertEquals(fakeWeather, viewModel.weather.value)
    }

    @Test
    fun `fetchDetail sets error state on failure`() = runTest(coroutineRule.testDispatcher) {
        val errorMessage = "City not found"
        coEvery { getWeatherUseCase("london") } returns Result.failure(Exception(errorMessage))
        val viewModel = WeatherDetailViewModel("london", getWeatherUseCase, strings)
        advanceUntilIdle()

        assertNull(viewModel.weather.value)
        assertNotNull(viewModel.error.value)
        assertEquals(errorMessage, viewModel.error.value)
    }

}