package com.example.androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.androidcourse.navigation.NavGraph
import com.example.androidcourse.ui.theme.CustomTheme
import com.example.androidcourse.ui.theme.ThemeEnum

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var currentTheme by rememberSaveable { mutableStateOf(ThemeEnum.PUMPKIN) }

            CustomTheme(theme = currentTheme) {
                NavGraph(onThemeChange = { currentTheme = it })
            }
        }
    }
}