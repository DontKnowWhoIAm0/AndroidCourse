package com.example.androidcourse.ui.navigation.auth.recovery

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.UserRepository
import com.example.androidcourse.utils.AuthManager
import kotlinx.coroutines.launch

class AccountRecoveryViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val authManager = AuthManager(application)

    private val _uiState = mutableStateOf(AccountRecoveryUiState())
    val uiState: State<AccountRecoveryUiState> = _uiState

    fun initUserId(userId: Int) {
        _uiState.value = _uiState.value.copy(userId = userId)
    }

    fun restoreAccount() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            userRepository.restoreUser(_uiState.value.userId)
            val user = userRepository.getUserById(_uiState.value.userId)
            user?.let {
                authManager.saveSession(it.email, it.id)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isSuccess = true
                )
            }
        }
    }

    fun deletePermanently() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            userRepository.delete(_uiState.value.userId)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isPermanentlyDeleted = true
            )
        }
    }
}