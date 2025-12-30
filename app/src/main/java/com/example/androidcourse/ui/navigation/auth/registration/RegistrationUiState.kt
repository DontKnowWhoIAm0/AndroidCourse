package com.example.androidcourse.ui.navigation.auth.registration

data class RegistrationUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)