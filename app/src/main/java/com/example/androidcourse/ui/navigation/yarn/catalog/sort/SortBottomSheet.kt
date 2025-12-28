package com.example.androidcourse.ui.navigation.yarn.catalog.sort

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import com.example.androidcourse.ui.navigation.yarn.catalog.CatalogUiState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    uiState: CatalogUiState,
    onSortSelected: (SortOption) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Выберите тип сортировки:", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(8.dp))

            SortOption.values().filter { it != uiState.sortOption }.forEach { option ->
                ListItem(
                    headlineContent = {Text(option.name.replace("_", " "))},
                    modifier = Modifier.clickable {
                        onSortSelected(option)
                        onDismiss()
                    }
                )
            }
        }
    }
}