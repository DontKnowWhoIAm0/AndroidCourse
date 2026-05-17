package com.example.androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.androidcourse.data.local.WeatherDatabase
import com.example.androidcourse.di.AppModule
import com.example.androidcourse.presentation.ui.WeatherScreen
import com.example.androidcourse.presentation.viewmodel.WeatherStringsImpl
import com.example.androidcourse.presentation.viewmodel.WeatherViewModel
import com.example.androidcourse.utils.DbConstants

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherStrings = WeatherStringsImpl(this)

        val api = AppModule.provideWeatherApi()
        val db = Room.databaseBuilder(applicationContext, WeatherDatabase::class.java, DbConstants.DB_NAME).fallbackToDestructiveMigration().build()
        val repo = AppModule.provideWeatherRepository(api, db.weatherDao(), weatherStrings)
        val useCase = AppModule.provideGetWeatherUseCase(repo)
        val viewModel = WeatherViewModel(useCase, weatherStrings)

        setContent {
            WeatherScreen(viewModel)
        }
    }
}
