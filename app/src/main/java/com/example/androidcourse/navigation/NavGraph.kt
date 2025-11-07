package com.example.androidcourse.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidcourse.ui.screen.EditScreen
import com.example.androidcourse.ui.screen.MessagesScreen
import com.example.androidcourse.ui.screen.NotificationScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavigationKeys.NOTIFICATION_PAGE,
        modifier = Modifier
    ) {
        composable(NavigationKeys.NOTIFICATION_PAGE) {
            NotificationScreen(
                navController = navController,
                innerPadding = innerPadding)
        }

        composable(NavigationKeys.EDITING_PAGE) {
            EditScreen(
                navController = navController,
                innerPadding = innerPadding)
        }

        composable(NavigationKeys.MESSAGES_PAGE) {
            MessagesScreen(
                navController = navController,
                innerPadding = innerPadding)
        }
    }
}