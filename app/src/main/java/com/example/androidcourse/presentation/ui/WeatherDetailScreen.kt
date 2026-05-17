package com.example.androidcourse.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidcourse.presentation.viewmodel.WeatherDetailViewModel

@Composable
fun WeatherDetailScreen(viewModel: WeatherDetailViewModel) {
    val weather = viewModel.weather.value
    val error = viewModel.error.value

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            error != null -> Text(text = error, color = MaterialTheme.colorScheme.error)
            weather == null -> CircularProgressIndicator()
            else -> Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = weather.cityName,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(text = "Температура: ${weather.temperature}°C")
                Text(text = "Описание: ${weather.description}")
                Text(text = "Влажность: ${weather.humidity}%")
                Text(text = "Ветер: ${weather.windSpeed} м/с")
            }
        }
    }
}