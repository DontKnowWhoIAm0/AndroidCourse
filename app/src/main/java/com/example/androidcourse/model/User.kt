package com.example.androidcourse.model

import java.util.Date

data class User(
    val id: Int = 0,
    val email: String,
    val password: String,
    val isDeleted: Boolean,
    val deleteDate: Date
)