package com.example.androidcourse.ui.navigation.yarn.add

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.YarnRepository
import com.example.androidcourse.model.Yarn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddYarnViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = YarnRepository(AppDatabase.getDatabase(application).yarnDao())

    private val _uiState = mutableStateOf(AddYarnUiState())
    val uiState: State<AddYarnUiState> = _uiState

    val isSaveEnabled: Boolean
        get() = with(uiState.value) { brand.isNotBlank() && composition.isNotBlank() &&
                skeinLength.isNotBlank() && weight.isNotBlank() && hookSize.isNotBlank() &&
                needleSize.isNotBlank() && !isLoading
        }

    fun onBrandChange(value: String) {
        _uiState.value = _uiState.value.copy(brand = value, error = null)
    }

    fun onCompositionChange(value: String) {
        _uiState.value = _uiState.value.copy(composition = value, error = null)
    }

    fun onSkeinLengthChange(value: String) {
        _uiState.value = _uiState.value.copy(skeinLength = value, error = null)
    }

    fun onWeightChange(value: String) {
        _uiState.value = _uiState.value.copy(weight = value, error = null)
    }

    fun onHookSizeChange(value: String) {
        _uiState.value = _uiState.value.copy(hookSize = value, error = null)
    }

    fun onNeedleSizeChange(value: String) {
        _uiState.value = _uiState.value.copy(needleSize = value, error = null)
    }

    fun saveYarn() {
        val state = _uiState.value

        val skeinLength = state.skeinLength.toIntOrNull()
        val weight = state.weight.toIntOrNull()
        val hookSize = state.hookSize.replace(",", ".").toFloatOrNull()
        val needleSize = state.needleSize.replace(",", ".").toFloatOrNull()

        if (skeinLength == null) {
            _uiState.value = state.copy(error = "Длина мотка должна быть целым числом!")
            return
        } else if (skeinLength > 30000) {
            _uiState.value = state.copy(error = "Длина мотка не может превышать 30000 метров!")
            return
        } else if (skeinLength <= 0) {
            _uiState.value = state.copy(error = "Длина мотка не может быть отрицательной!")
            return
        }

        if (weight == null) {
            _uiState.value = state.copy(error = "Вес мотка должен быть целым числом!")
            return
        } else if (weight > 1000) {
            _uiState.value = state.copy(error = "Вес мотка не может превышать 1000 грамм!")
            return
        } else if (weight <= 0) {
            _uiState.value = state.copy(error = "Вес мотка не может быть отрицательным!")
            return
        }

        if (hookSize == null) {
            _uiState.value = state.copy(error = "Размер крючка должен быть числом!")
            return
        } else if (hookSize > 40) {
            _uiState.value = state.copy(error = "Размер крючка не должен превышать 40 мм!")
            return
        } else if (hookSize <= 0) {
            _uiState.value = state.copy(error = "Размер крючка не может быть отрицательным!")
            return
        }

        if (needleSize == null) {
            _uiState.value = state.copy(error = "Размер спиц должен быть числом!")
            return
        } else if (needleSize > 50) {
            _uiState.value = state.copy(error = "Размер спиц не должен превышать 50 мм!")
            return
        } else if (needleSize <= 0) {
            _uiState.value = state.copy(error = "Размер спиц не может быть отрицательным!")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true)

            repository.addYarn(
                Yarn(
                    brand = state.brand,
                    composition = state.composition,
                    skeinLength = skeinLength,
                    weight = weight,
                    hookSize = hookSize,
                    needleSize = needleSize
                )
            )

            delay(500)

            _uiState.value = state.copy(
                isLoading = false,
                isSuccess = true
            )
        }
    }
}