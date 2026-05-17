package com.example.androidcourse.di

import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import com.example.androidcourse.presentation.viewmodel.WeatherDetailViewModel
import dagger.Module
import dagger.Provides

@Module
object WeatherDetailModule {

    @Provides
    fun provideWeatherDetailViewModel(
        @CityName cityName: String,
        getWeatherUseCase: GetWeatherUseCase
    ): WeatherDetailViewModel {
        return WeatherDetailViewModel(cityName, getWeatherUseCase)
    }
}