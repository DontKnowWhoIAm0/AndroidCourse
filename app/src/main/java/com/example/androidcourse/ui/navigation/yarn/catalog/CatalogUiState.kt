package com.example.androidcourse.ui.navigation.yarn.catalog

import com.example.androidcourse.model.Yarn
import com.example.androidcourse.ui.navigation.yarn.catalog.sort.SortOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class CatalogUiState(
    val yarnFlow: Flow<List<Yarn>> = emptyFlow(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val sortOption: SortOption = SortOption.NONE,
    val showBottomSheet: Boolean = false
)

