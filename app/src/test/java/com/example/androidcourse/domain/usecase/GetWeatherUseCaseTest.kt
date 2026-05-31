package com.example.androidcourse.domain.usecase

import com.example.androidcourse.domain.model.Weather
import com.example.androidcourse.domain.repository.WeatherRepository
import com.example.androidcourse.utils.WeatherResult
import com.example.androidcourse.utils.database.DataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class GetWeatherUseCaseTest {

    private val repository: WeatherRepository = mockk()
    private lateinit var useCase: GetWeatherUseCase

    private val fakeWeather = Weather(
        cityName = "london",
        temperature = 15.0,
        description = "cloudy",
        icon = "04d",
        humidity = 80,
        windSpeed = 5.0
    )

    @Before
    fun setUp() {
        useCase = GetWeatherUseCase(repository)
    }

    @Test
    fun `invoke calls repository once and returns correct weather`() = runTest {
        val city = "london"
        val expected = Result.success(WeatherResult(fakeWeather, DataSource.REMOTE))

        coEvery { repository.getWeather(city) } returns expected

        val result = useCase("london")

        assertTrue(result.isSuccess)
        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getWeather(city) }
    }
}