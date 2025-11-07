package com.example.androidcourse

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.androidcourse.navigation.BottomBar
import com.example.androidcourse.navigation.NavGraph


class MainActivity : ComponentActivity() {

    // Ноутбук не тянет эмулятор, запускаю на телефоне, поэтому приходится запрашивать разрешение
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//                Toast.makeText(this, "Разрешение на уведомления получено", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(
//                    this,
//                    "Невозможно показать уведомления без разрешения",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //checkNotificationPermission()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()

            Scaffold(
                bottomBar = { BottomBar(navController = navController) },
            ) { innerPadding ->
                NavGraph(
                    navController = navController,
                    innerPadding = innerPadding
                )
            }

        }
    }

//    private fun checkNotificationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }
}