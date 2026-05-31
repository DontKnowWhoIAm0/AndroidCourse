package com.example.androidcourse.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.androidcourse.R
import com.example.androidcourse.presentation.viewmodel.WeatherState
import com.example.androidcourse.presentation.viewmodel.WeatherViewModel
import com.example.androidcourse.utils.database.DataSource

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onCitySelected: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var city by remember { mutableStateOf("") }
    val state by viewModel.state

    val onFetchClick = remember(city) {
        { viewModel.fetchWeather(city) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text(stringResource(id = R.string.enter_city_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onFetchClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.get_weather))
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is WeatherState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            is WeatherState.Success -> {
                val successState = state as WeatherState.Success
                WeatherDetails(successState.result.weather)

                val cityName = successState.result.weather.cityName
                val onDetailsClick = remember(cityName) { { onCitySelected(cityName) } }
                Button(onClick = onDetailsClick, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(id = R.string.details_button))
                }

                val sourceText = if (successState.result.source == DataSource.REMOTE)
                    stringResource(id = R.string.data_received_remote)
                else
                    stringResource(id = R.string.data_received_cache)

                LaunchedEffect(state) {
                    Toast.makeText(context, sourceText, Toast.LENGTH_SHORT).show()
                }
            }
            is WeatherState.Error -> Text(
                stringResource(id = R.string.error, (state as WeatherState.Error).message),
                color = Color.Red,
                modifier = Modifier.padding(top = 32.dp)
            )
            WeatherState.Idle -> {}
        }
    }
}