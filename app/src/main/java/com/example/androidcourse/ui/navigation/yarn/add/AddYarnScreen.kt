package com.example.androidcourse.ui.navigation.yarn.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddYarnScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: AddYarnViewModel = viewModel()
) {
    val state by viewModel.uiState

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
        }
    }

    Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Добавить пряжу",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = state.brand,
                onValueChange = viewModel::onBrandChange,
                label = { Text("Бренд") },
                singleLine = true
            )

            OutlinedTextField(
                value = state.composition,
                onValueChange = viewModel::onCompositionChange,
                label = { Text("Состав") },
                singleLine = true
            )

            OutlinedTextField(
                value = state.skeinLength,
                onValueChange = viewModel::onSkeinLengthChange,
                label = { Text("Длина мотка (м)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = state.weight,
                onValueChange = viewModel::onWeightChange,
                label = { Text("Вес мотка (г)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = state.hookSize,
                onValueChange = viewModel::onHookSizeChange,
                label = { Text("Размер крючка") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            OutlinedTextField(
                value = state.needleSize,
                onValueChange = viewModel::onNeedleSizeChange,
                label = { Text("Размер спиц") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            state.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = viewModel::saveYarn,
                enabled = viewModel.isSaveEnabled,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
