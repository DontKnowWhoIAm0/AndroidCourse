package com.example.androidcourse.ui.navigation.auth.registration

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())

    private val _uiState = mutableStateOf(RegistrationUiState())
    val uiState: State<RegistrationUiState> = _uiState

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value, error = null)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value, error = null)
    }

    fun registration() {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Все поля обязательны")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true)

            val result = userRepository.registration(
                email = state.email,
                password = state.password
            )

            _uiState.value = if (result.isSuccess) {
                state.copy(isLoading = false, isSuccess = true)
            } else {
                state.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message
                )
            }
        }
    }
}