package com.example.androidcourse.ui.navigation.auth.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androidcourse.ui.navigation.graph.NavigationKeys
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: RegistrationViewModel = viewModel()
) {
    val state by viewModel.uiState

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(NavigationKeys.LOGIN) {
                popUpTo(NavigationKeys.REGISTRATION) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            singleLine = true,
            isError = state.error != null
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = state.error != null
        )

        state.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = viewModel::registration,
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Зарегистрироваться")
            }
        }

        TextButton(
            onClick = {
                navController.navigate(NavigationKeys.LOGIN)
            }
        ) {
            Text("Уже есть аккаунт? Войти")
        }
    }
}