package com.example.androidcourse.ui.navigation.auth.recovery

data class AccountRecoveryUiState(
    val userId: Int = 0,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isPermanentlyDeleted: Boolean = false
)