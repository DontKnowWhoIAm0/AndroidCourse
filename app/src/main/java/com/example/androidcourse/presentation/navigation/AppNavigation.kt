package com.example.androidcourse.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidcourse.di.AppComponent
import com.example.androidcourse.presentation.ui.WeatherDetailScreen
import com.example.androidcourse.presentation.ui.WeatherScreen

sealed class Screen(val route: String) {
    object Weather : Screen("weather")
    object Detail : Screen("detail/{cityName}") {
        fun createRoute(cityName: String) = "detail/$cityName"
    }
}

@Composable
fun AppNavigation(appComponent: AppComponent) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Weather.route) {

        composable(Screen.Weather.route) {
            WeatherScreen(
                viewModel = appComponent.weatherViewModel(),
                onCitySelected = { cityName ->
                    navController.navigate(Screen.Detail.createRoute(cityName))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("cityName") { type = NavType.StringType })
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: return@composable

            val detailComponent = appComponent
                .weatherDetailComponentFactory()
                .create(cityName)

            WeatherDetailScreen(viewModel = detailComponent.viewModel())
        }
    }
}