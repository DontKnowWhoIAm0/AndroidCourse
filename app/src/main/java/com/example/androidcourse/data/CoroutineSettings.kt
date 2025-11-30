package com.example.androidcourse.data

data class CoroutineSettings(
    val count: Int = 10,
    val dispatcher: DispatcherType = DispatcherType.DEFAULT,
    val sequential: Boolean = true,
    val parallel: Boolean = false,
    val delayedStart: Boolean = false
)