package com.example.androidcourse.ui.navigation.auth.login

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.R
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.UserRepository
import com.example.androidcourse.utils.AuthManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val authManager = AuthManager(application)
    private val context = getApplication<Application>()

    // Для того, чтобы бд была открыта всегда
    init {
        viewModelScope.launch {
            userRepository.observeUsers().collect {
            }
        }
    }

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    val isLoginEnabled: Boolean
        get() = _uiState.value.email.isNotBlank() && _uiState.value.password.isNotBlank() &&
                _uiState.value.error == null && !_uiState.value.isLoading

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value, error = null)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value, error = null)
    }

    fun login() {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = context.getString(R.string.empty_fields))
            return
        }

        if (!isEmailValid(state.email)) {
            _uiState.value = state.copy(error = context.getString(R.string.incorrect_email))
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true)

            val result = userRepository.login(state.email, state.password)
            delay(500)

            if (result) {
                val user = userRepository.getUserByEmail(state.email)
                user?.let {
                    authManager.saveSession(state.email, it.id)
                }
                _uiState.value = state.copy(isLoading = false, isSuccess = true)
            } else {
                _uiState.value = state.copy(
                    isLoading = false,
                    error = context.getString(R.string.login_failed)
                )
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}