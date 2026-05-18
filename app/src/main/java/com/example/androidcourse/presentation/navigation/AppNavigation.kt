package com.example.androidcourse.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidcourse.di.app.AppComponent
import com.example.androidcourse.presentation.ui.WeatherDetailScreen
import com.example.androidcourse.presentation.ui.WeatherScreen
import com.example.androidcourse.presentation.ui.OnboardingBottomSheet
import com.example.androidcourse.utils.analytics.AnalyticsLogger
import com.example.androidcourse.utils.prefs.OnboardingPreferences

sealed class Screen(val route: String) {
    object Weather : Screen("weather")
    object Detail : Screen("detail/{cityName}") {
        fun createRoute(cityName: String) = "detail/$cityName"
    }
}

private object ScreenNames {
    const val WEATHER = "WeatherScreen"
    const val DETAIL_PREFIX = "WeatherDetailScreen"
    const val UNKNOWN = "Unknown"
}

@Composable
fun AppNavigation(appComponent: AppComponent) {
    val navController = rememberNavController()
    val context = LocalContext.current

    var showOnboarding by remember {
        mutableStateOf(!OnboardingPreferences.isOnboardingShown(context))
    }


    DisposableEffect(navController) {
        val listener = androidx.navigation.NavController.OnDestinationChangedListener { _, destination, arguments ->
            val screenName = when (destination.route) {
                Screen.Weather.route -> ScreenNames.WEATHER
                Screen.Detail.route -> {
                    val city = arguments?.getString("cityName") ?: ScreenNames.UNKNOWN
                    "${ScreenNames.DETAIL_PREFIX}(city=$city)"
                }
                else -> destination.route ?: ScreenNames.UNKNOWN
            }
            AnalyticsLogger.logScreenView(screenName)
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Scaffold { paddingValues ->

        NavHost(navController = navController, startDestination = Screen.Weather.route, modifier = Modifier.padding(paddingValues)) {

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

        if (showOnboarding) {
            OnboardingBottomSheet(
                onDismiss = {
                    OnboardingPreferences.markOnboardingShown(context)
                    showOnboarding = false
                }
            )
        }

    }

}