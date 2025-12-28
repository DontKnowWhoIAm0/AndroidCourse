package com.example.androidcourse.ui.navigation.yarn.catalog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.androidcourse.ui.navigation.graph.NavigationKeys
import com.example.androidcourse.ui.navigation.yarn.catalog.sort.SortBottomSheet
import com.example.androidcourse.ui.navigation.yarn.catalog.sort.SortOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: CatalogViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val yarns by uiState.yarnFlow.collectAsState(initial = emptyList())
    var showSortSheet by remember { mutableStateOf(false) }

    BackHandler(enabled = true) { }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Каталог пряжи") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(NavigationKeys.PROFILE) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "В профиль",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {

                FloatingActionButton(
                    onClick = { showSortSheet = true },
                    modifier = Modifier.size(64.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (uiState.sortOption == SortOption.NONE) {
                            Icon(
                                Icons.Default.Sort,
                                contentDescription = "Сортировать список пряжи",
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Text(
                                text = stringResource(uiState.sortOption.titleRes),
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                FloatingActionButton(
                    onClick = { navController.navigate(NavigationKeys.ADD_YARN) },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(Icons.Default.Add, "Добавить пряжу")
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                ErrorCard(
                    error = uiState.error!!,
                    onDismiss = viewModel::dismissError
                )
            } else {
                if (yarns.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Пряжи еще нет. Добавьте первую!")
                    }
                } else {
                    LazyColumn {
                        items(yarns) { yarn ->
                            YarnItem(
                                yarn = yarn,
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }

        if (showSortSheet) {
            SortBottomSheet(
                uiState = uiState,
                onSortSelected = { sortOption ->
                    viewModel.onSortChanged(sortOption)
                    showSortSheet = false
                },
                onDismiss = { showSortSheet = false }
            )
        }
    }
}

@Composable
private fun ErrorCard(error: String, onDismiss: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(error, color = MaterialTheme.colorScheme.onErrorContainer)
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onDismiss) { Text("Повторить") }
        }
    }
}