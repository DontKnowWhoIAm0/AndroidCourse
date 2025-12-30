package com.example.androidcourse.ui.navigation.auth.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidcourse.R
import com.example.androidcourse.ui.navigation.graph.NavigationKeys

@Composable
fun AccountRecoveryScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    userId: Int,
    viewModel: AccountRecoveryViewModel = viewModel()
) {
    val state by viewModel.uiState

    LaunchedEffect(userId) {
        viewModel.initUserId(userId)
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(NavigationKeys.CATALOG) {
                popUpTo(NavigationKeys.LOGIN) { inclusive = true }
                popUpTo(NavigationKeys.ACCOUNT_RECOVERY) { inclusive = true }
            }
        }
    }

    LaunchedEffect(state.isPermanentlyDeleted) {
        if (state.isPermanentlyDeleted) {
            navController.navigate(NavigationKeys.LOGIN) {
                popUpTo(NavigationKeys.ACCOUNT_RECOVERY) { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.padding(innerPadding).fillMaxSize().imePadding()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.account_deleted_title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.account_deleted_message),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { viewModel.restoreAccount() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text(stringResource(R.string.restore_account))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { viewModel.deletePermanently() },
                enabled = !state.isLoading
            ) {
                Text(stringResource(R.string.delete_permanently))
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}