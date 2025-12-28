package com.example.androidcourse.ui.navigation.yarn.catalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.data.db.AppDatabase
import com.example.androidcourse.data.repository.YarnRepository
import com.example.androidcourse.ui.navigation.yarn.catalog.sort.SortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogViewModel(application: Application) : AndroidViewModel(application) {

    private val yarnRepository = YarnRepository(AppDatabase.getDatabase(application).yarnDao())

    private val _uiState = MutableStateFlow(CatalogUiState(isLoading = true))
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        loadYarns()
    }

    private fun loadYarns(sortOption: SortOption = SortOption.BRAND_ASC) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val yarns = when (sortOption) {
                    SortOption.BRAND_ASC -> yarnRepository.getAllYarnsSortedByBrand()
                    SortOption.BRAND_DESC -> yarnRepository.getAllYarnsSortedByBrandDesc()
                    SortOption.WEIGHT_ASC -> yarnRepository.getAllYarnsSortedByWeight()
                    SortOption.WEIGHT_DESC -> yarnRepository.getAllYarnsSortedByWeightDesc()
                    SortOption.LENGTH_ASC -> yarnRepository.getAllYarnsSortedBySkeinLength()
                    SortOption.LENGTH_DESC -> yarnRepository.getAllYarnsSortedBySkeinLengthDesc()
                    else -> yarnRepository.getAllYarns()
                }
                _uiState.value = _uiState.value.copy(yarnFlow = yarns, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Ошибка загрузки: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    fun onSortChanged(sortOption: SortOption) {
        _uiState.value = _uiState.value.copy(sortOption = sortOption)
        loadYarns(sortOption)
    }

    fun toggleBottomSheet() {
        _uiState.value = _uiState.value.copy(
            showBottomSheet = !_uiState.value.showBottomSheet
        )
    }

    fun dismissError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}