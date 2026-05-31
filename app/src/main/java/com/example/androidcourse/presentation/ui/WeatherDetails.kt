package com.example.androidcourse.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.androidcourse.R
import com.example.androidcourse.domain.model.Weather
import com.example.androidcourse.utils.network.ApiParams

@Composable
fun WeatherDetails(weather: Weather) {

    val cityName = remember(weather) { weather.cityName.capitalize() }
    val description = remember(weather) { weather.description.replaceFirstChar { it.uppercaseChar() } }
    val iconUrl = remember(weather) { "${ApiParams.ICON_BASE_URL}${weather.icon}@2x.png" }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0F7FA), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(cityName, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF00796B))

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = rememberAsyncImagePainter(iconUrl),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(
                id = R.string.temperature_and_description,
                weather.temperature,
                description
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