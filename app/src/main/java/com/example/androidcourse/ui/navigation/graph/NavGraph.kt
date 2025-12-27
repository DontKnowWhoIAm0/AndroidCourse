package com.example.androidcourse.ui.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    isUserLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn)
            NavigationKeys.CATALOG
        else
            NavigationKeys.LOGIN,
        modifier = Modifier
    ) {
        composable(NavigationKeys.LOGIN) {
            LoginScreen(
                navController = navController,
                innerPadding = innerPadding)
        }

        composable(NavigationKeys.REGISTRATION) {
            RegistrationScreen(
                navController = navController,
                innerPadding = innerPadding)
        }

        composable(NavigationKeys.CATALOG) {
            CatalogScreen(
                navController = navController,
                innerPadding = innerPadding)
        }

        composable(NavigationKeys.ADD_YARN) {
            AddScreen(
                navController = navController,
                innerPadding = innerPadding)
        }

        composable(NavigationKeys.PROFILE) {
            ProfileScreen(
                navController = navController,
                innerPadding = innerPadding)
        }
    }
}