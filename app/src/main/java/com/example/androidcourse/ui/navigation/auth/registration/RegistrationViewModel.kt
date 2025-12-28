package com.example.androidcourse.ui.navigation.auth.registration

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.UserRepository
import com.example.androidcourse.utils.PasswordHasher
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())

    private val _uiState = mutableStateOf(RegistrationUiState())
    val uiState: State<RegistrationUiState> = _uiState

    val isRegisterEnabled: Boolean
        get() = _uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank() &&
                _uiState.value.error == null && !_uiState.value.isLoading

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

        if (!isEmailValid(state.email)) {
            _uiState.value = state.copy(error = "Неверный формат email")
            return
        }

        if (!isPasswordStrong(state.password)) {
            _uiState.value = state.copy(
                error = "Пароль должен быть минимум 8 символов, содержать заглавную, прописную буквы и цифру"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true)

            val salt = PasswordHasher.generateSalt()
            val hashedPassword = PasswordHasher.hash(state.password, salt)

            val result = userRepository.registration(
                email = state.email,
                password = hashedPassword,
                salt = salt
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

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordStrong(password: String): Boolean {
        val minLength = 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }

        return password.length >= minLength && hasUpperCase && hasLowerCase && hasDigit
    }
}