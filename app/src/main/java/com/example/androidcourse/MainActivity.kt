package com.example.androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.androidcourse.ui.screen.CoroutineViewModel
import com.example.androidcourse.ui.screen.MainScreen
import com.example.androidcourse.ui.theme.AndroidCourseTheme

class MainActivity : ComponentActivity() {

    private val viewModel: CoroutineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCourseTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}