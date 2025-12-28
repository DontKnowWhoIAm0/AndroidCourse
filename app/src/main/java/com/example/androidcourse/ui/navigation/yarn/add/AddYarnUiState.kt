package com.example.androidcourse.ui.navigation.yarn.add

data class AddYarnUiState(
    val brand: String = "",
    val composition: String = "",
    val skeinLength: String = "",
    val weight: String = "",
    val hookSize: String = "",
    val needleSize: String = "",

    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)