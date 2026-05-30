package com.example.androidcourse.di.app

import android.content.Context
import com.example.androidcourse.MainActivity
import com.example.androidcourse.di.database.DatabaseModule
import com.example.androidcourse.di.network.NetworkModule
import com.example.androidcourse.di.StringsModule
import com.example.androidcourse.di.weather_details.WeatherDetailComponent
import com.example.androidcourse.presentation.viewmodel.WeatherViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, NetworkModule::class, StringsModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)

    fun weatherDetailComponentFactory(): WeatherDetailComponent.Factory

    fun weatherViewModel(): WeatherViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}