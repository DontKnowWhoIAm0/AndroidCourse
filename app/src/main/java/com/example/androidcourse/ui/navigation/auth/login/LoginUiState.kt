package com.example.androidcourse.ui.navigation.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isShimmerLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,

    val isAccountDeleted: Boolean = false,
    val deletedUserId: Int? = null
)