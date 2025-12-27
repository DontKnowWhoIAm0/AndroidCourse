package com.example.androidcourse.model

data class Yarn(
    val id: Int = 0,
    val brand: String,
    val composition: String,
    val skeinLength: Int,
    val thickness: Int,
    val hookSize: Float,
    val needleSize: Float
)