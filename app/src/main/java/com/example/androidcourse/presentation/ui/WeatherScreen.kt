package com.example.androidcourse.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil3.compose.rememberAsyncImagePainter
import com.example.androidcourse.R
import com.example.androidcourse.domain.model.Weather
import com.example.androidcourse.presentation.viewmodel.WeatherState
import com.example.androidcourse.presentation.viewmodel.WeatherViewModel
import com.example.androidcourse.utils.ApiParams
import com.example.androidcourse.utils.DataSource

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onCitySelected: (String) -> Unit = {}
) {
    val context = LocalContext.current

    var city by remember { mutableStateOf("") }
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()
        .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text(stringResource(id = R.string.enter_city_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { viewModel.fetchWeather(city) }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.get_weather))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { throw RuntimeException("Test Crashlytics crash") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Краш тест")
        }

        when(state) {
            is WeatherState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            is WeatherState.Success -> {
                WeatherDetails(state.result.weather)

                Button(
                    onClick = { onCitySelected(state.result.weather.cityName) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Подробнее")
                }

                val sourceText = if (state.result.source == DataSource.REMOTE)
                    stringResource(id = R.string.data_received_remote)
                else
                    stringResource(id = R.string.data_received_cache)
                LaunchedEffect(state) {
                    Toast.makeText(context, sourceText, Toast.LENGTH_SHORT).show()
                }
            }
            is WeatherState.Error -> Text(stringResource(id = R.string.error, state.message), color = Color.Red, modifier = Modifier.padding(top = 32.dp))
            WeatherState.Idle -> {}
        }
    }
}

@Composable
fun WeatherDetails(weather: Weather) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0F7FA), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(weather.cityName.capitalize(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00796B))

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = rememberAsyncImagePainter("${ApiParams.ICON_BASE_URL}${weather.icon}@2x.png"),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(
                id = R.string.temperature_and_description,
                weather.temperature,
                weather.description.replaceFirstChar { it.uppercaseChar() }
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.humidity, weather.humidity), fontSize = 16.sp)
            Text(stringResource(id = R.string.wind, weather.windSpeed), fontSize = 16.sp)
        }
    }
}