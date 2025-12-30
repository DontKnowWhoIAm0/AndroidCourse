package com.example.androidcourse.ui.navigation.profile

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.UserRepository
import com.example.androidcourse.utils.AuthManager
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(AppDatabase.getDatabase(application).userDao())
    private val authManager = AuthManager(application)

    private val _uiState = mutableStateOf(ProfileUiState())
    val uiState: State<ProfileUiState> = _uiState
    private val _showDeleteDialog = mutableStateOf(false)
    val showDeleteDialog: State<Boolean> = _showDeleteDialog

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

    fun deleteAccount() {
        viewModelScope.launch {
            val currentUserId = authManager.getUserId() ?: return@launch
            userRepository.softDeleteUser(currentUserId)
            authManager.logout()
            _uiState.value = _uiState.value.copy(isLoggedOut = true)
        }
    }

    fun showDeleteDialog(show: Boolean) {
        _showDeleteDialog.value = show
    }
}