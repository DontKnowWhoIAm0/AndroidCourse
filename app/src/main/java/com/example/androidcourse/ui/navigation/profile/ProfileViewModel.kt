package com.example.androidcourse.ui.navigation.profile

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.androidcourse.utils.AuthManager

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val authManager = AuthManager(application)
    private val _uiState = mutableStateOf(ProfileUiState())
    val uiState: State<ProfileUiState> = _uiState

    init { loadUser() }

    private fun loadUser() {
        if (!authManager.isLoggedIn()) {
            _uiState.value = _uiState.value.copy(isLoggedOut = true)
            return
        }

        _uiState.value = ProfileUiState(
            email = authManager.getUserEmail().orEmpty(),
            userId = authManager.getUserId()
        )
    }

    fun logout() {
        authManager.logout()
        _uiState.value = _uiState.value.copy(isLoggedOut = true)
    }
}