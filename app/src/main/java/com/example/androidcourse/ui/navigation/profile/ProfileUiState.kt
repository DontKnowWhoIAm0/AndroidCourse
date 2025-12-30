package com.example.androidcourse.ui.navigation.profile

data class ProfileUiState(
    val email: String = "",
    val userId: Int = -1,
    val isLoggedOut: Boolean = false
)