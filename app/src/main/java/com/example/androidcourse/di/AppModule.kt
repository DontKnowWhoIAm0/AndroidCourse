package com.example.androidcourse.di

import com.example.androidcourse.data.repository.WeatherRepositoryImpl
import com.example.androidcourse.domain.repository.WeatherRepository
import com.example.androidcourse.domain.usecase.GetWeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {
        @Provides
        @Singleton
        fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
            return GetWeatherUseCase(repository)
        }
    }
}