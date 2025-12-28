package com.example.androidcourse.ui.navigation.yarn.catalog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun CatalogScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    BackHandler(enabled = true) { }

    Text("AБОБА")
}