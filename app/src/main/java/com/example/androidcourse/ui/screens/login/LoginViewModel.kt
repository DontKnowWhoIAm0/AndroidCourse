package com.example.androidcourse.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidcourse.R

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var emailError by mutableStateOf(0)
        private set

    var passwordError by mutableStateOf(0)
        private set

    var blankError by mutableStateOf(0)
        private set

    var passwordVisible by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        if (emailError != 0) {emailError = 0}
        if (email.isNotBlank() && password.isNotBlank()) {blankError = 0}
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        if (passwordError != 0) {passwordError = 0}
        if (email.isNotBlank() && password.isNotBlank()) {blankError = 0}
    }

    fun onTogglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun validateFields(): Boolean {
        var isValidate = true

        if (email.isBlank()) {
            blankError = R.string.blankError
            isValidate = false
        } else {
            val isEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
            if (!isEmail) {
                emailError = R.string.emailError
                isValidate = false
            }
        }

        if (password.isBlank()) {
            blankError = R.string.blankError
            isValidate = false
        } else if (password.length < 8) {
            passwordError = R.string.passwordError
            isValidate = false
        }

        return isValidate
    }
}