package com.example.androidcourse.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidcourse.R

@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.height(64.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = stringResource(R.string.notification_page),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(bottom = 0.dp)
                )
            },
            label = {
                Text(
                    stringResource(R.string.notification_page),
                    fontSize = 10.sp
                )
            },
            selected = currentRoute == NavigationKeys.NOTIFICATION_PAGE,
            onClick = {
                navController.navigate(NavigationKeys.NOTIFICATION_PAGE) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = stringResource(R.string.editing_page),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(bottom = 0.dp)
                )
            },
            label = {
                Text(
                    stringResource(R.string.editing_page),
                    fontSize = 10.sp
                )
            },
            selected = currentRoute == NavigationKeys.EDITING_PAGE,
            onClick = {
                navController.navigate(NavigationKeys.EDITING_PAGE) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = stringResource(R.string.messages_page),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(bottom = 0.dp)
                )
            },
            label = {
                Text(
                    stringResource(R.string.messages_page),
                    fontSize = 10.sp
                )
            },
            selected = currentRoute == NavigationKeys.MESSAGES_PAGE,
            onClick = {
                navController.navigate(NavigationKeys.MESSAGES_PAGE) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}