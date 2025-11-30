package com.example.androidcourse.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidcourse.data.DispatcherType

@Composable
fun MainScreen(viewModel: CoroutineViewModel, modifier: Modifier = Modifier) {

    val settings = viewModel.settings
    val isRunning = viewModel.isRunning.value
    val completed = viewModel.completed.value
    val toastMessage = viewModel.toastMessage.value
    val runSettings = viewModel.currentSettings

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.toastMessage.value = null
        }
    }

    LaunchedEffect(viewModel.showSnackbar.value) {
        if (viewModel.showSnackbar.value) {
            snackbarHostState.showSnackbar("Ошибка Snackbar")
            viewModel.showSnackbar.value = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Запуск корутин",
                    fontSize = 20.sp,
                    modifier = modifier.align(Alignment.CenterHorizontally)
                )

                Text("Количество корутин: ${settings.count}")
                Slider(
                    value = settings.count.toFloat(),
                    onValueChange = { viewModel.onCountChange(it.toInt()) },
                    valueRange = 10f..100f,
                    steps = 17
                )

                DispatcherDropdown(
                    modifier = Modifier.fillMaxWidth(),
                    selected = settings.dispatcher
                ) {
                    viewModel.onDispatcherChange(it)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Последовательное выполнение")
                    Switch(
                        checked = settings.sequential,
                        onCheckedChange = { viewModel.onSequentialToggle(it) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Параллельное выполнение")
                    Switch(
                        checked = settings.parallel,
                        onCheckedChange = { viewModel.onParallelToggle(it) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Отложенный запуск")
                    Switch(
                        checked = settings.delayedStart,
                        onCheckedChange = { viewModel.onDelayedToggle(it) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Работа в бэкграунде")
                    Switch(
                        checked = settings.runInBackground,
                        onCheckedChange = { viewModel.onRunInBackgroundToggle(it) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!isRunning) {
                Button(
                    onClick = { viewModel.onStartClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Запуск")
                }
            } else {
                Column(
                    modifier = modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        progress = completed / runSettings!!.count.toFloat(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text("Выполнено: $completed / ${runSettings.count}")
                    Button(
                        onClick = { viewModel.onCancelClicked() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Отмена")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun DispatcherDropdown(
    modifier: Modifier = Modifier,
    selected: DispatcherType,
    onSelect: (DispatcherType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var buttonWidth by remember { mutableStateOf(0) }

    Box {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    buttonWidth = coordinates.size.width
                }
        ) {
            Text(selected.name)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { buttonWidth.toDp() })
        ) {
            DispatcherType.values().forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = {
                        onSelect(type)
                        expanded = false
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}