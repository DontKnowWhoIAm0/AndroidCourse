package com.example.androidcourse.util

object NotificationIDsController {
    private var id = 1;

    fun getId(): Int = id++
}