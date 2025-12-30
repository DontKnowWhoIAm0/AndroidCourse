package com.example.androidcourse.ui.navigation.auth.registration

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.R
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.UserRepository
import com.example.androidcourse.utils.PasswordHasher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val context = getApplication<Application>()

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
            _uiState.value = state.copy(error = context.getString(R.string.empty_fields))
            return
        }

        if (!isEmailValid(state.email)) {
            _uiState.value = state.copy(error = context.getString(R.string.incorrect_email))
            return
        }

        if (!isPasswordStrong(state.password)) {
            _uiState.value = state.copy(error = context.getString(R.string.incorrect_type_of_passwords))
            return
        }

        viewModelScope.launch {
            if (userRepository.isEmailExists(state.email)) {
                _uiState.value = state.copy(error = context.getString(R.string.email_exists))
                return@launch
            }

            _uiState.value = state.copy(isLoading = true)
            val salt = PasswordHasher.generateSalt()
            val hashedPassword = PasswordHasher.hash(state.password, salt)

            userRepository.registration(state.email, hashedPassword, salt)
            delay(500)
            _uiState.value = state.copy(isLoading = false, isSuccess = true)
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