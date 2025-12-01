package com.example.androidcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.androidcourse.ui.screen.CoroutineViewModel
import com.example.androidcourse.ui.screen.MainScreen
import com.example.androidcourse.ui.theme.AndroidCourseTheme

class MainActivity : ComponentActivity() {

    private val viewModel: CoroutineViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setContext(applicationContext)
        enableEdgeToEdge()

        ProcessLifecycleOwner.get().lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStop(owner: LifecycleOwner) {
                if (!viewModel.runInBackground) {
                    viewModel.stopCoroutinesOnBackground()
                }
            }

            override fun onStart(owner: LifecycleOwner) {
                if (!viewModel.runInBackground) {
                    viewModel.restartStoppedCoroutines()
                }
            }
        })

        setContent {
            AndroidCourseTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}