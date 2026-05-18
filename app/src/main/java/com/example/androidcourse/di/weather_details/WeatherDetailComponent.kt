package com.example.androidcourse.di.weather_details

import com.example.androidcourse.presentation.viewmodel.WeatherDetailViewModel
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CityName

@Subcomponent(modules = [WeatherDetailModule::class])
interface WeatherDetailComponent {

    fun viewModel(): WeatherDetailViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance @CityName cityName: String): WeatherDetailComponent
    }
}