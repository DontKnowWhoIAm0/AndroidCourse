package com.example.androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.androidcourse.ui.navigation.graph.NavGraph
import androidx.navigation.compose.rememberNavController
import com.example.androidcourse.ui.theme.AndroidCourseTheme
import com.example.androidcourse.utils.AuthManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val authManager = remember { AuthManager(this) }
            val isUserLoggedIn by remember { mutableStateOf(authManager.isLoggedIn()) }

            AndroidCourseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        innerPadding = innerPadding,
                        isUserLoggedIn = false //isUserLoggedIn
                    )
                }
            }
        }
    }
}