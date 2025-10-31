package com.example.androidcourse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidcourse.data.NavigationKeys
import com.example.androidcourse.ui.screens.addnote.AddNoteScreen
import com.example.androidcourse.ui.screens.home.HomeScreen
import com.example.androidcourse.ui.screens.login.LoginScreen
import com.example.androidcourse.ui.theme.ThemeEnum


@Composable
fun NavGraph(onThemeChange: (ThemeEnum) -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationKeys.LOGIN
    ) {
        composable(NavigationKeys.LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(NavigationKeys.HOME) { backStackEntry ->
            val email = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>(NavigationKeys.EMAIL) ?: ""
            HomeScreen(
                navController = navController,
                email = email,
                onThemeChange = onThemeChange
            )
        }

        composable(NavigationKeys.ADD_NOTE) {
            AddNoteScreen(navController = navController)
        }

        composable(NavigationKeys.EDIT_NOTE) {
            val id = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>(NavigationKeys.ID)
            AddNoteScreen(navController = navController, noteId = id)
        }
    }
}
