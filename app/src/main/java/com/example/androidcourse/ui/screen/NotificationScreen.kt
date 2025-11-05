package com.example.androidcourse.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidcourse.data.Importance
import com.example.androidcourse.R
import com.example.androidcourse.util.createNotification

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = viewModel()
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    val importances = Importance.entries
    val context = LocalContext.current

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.onTitleChange(it)},
                label = { Text(stringResource(R.string.title)) },
                isError = viewModel.titleError != 0,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.titleError != 0) {
                Text(stringResource(R.string.titleError))
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = viewModel.text,
                onValueChange = { viewModel.onTextChange(it)},
                label = { Text(stringResource(R.string.text)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.expandNotification))
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = viewModel.expandable,
                    onCheckedChange = { viewModel.onExpandableChange(it)},
                    enabled = viewModel.text.isNotBlank()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box {
                OutlinedButton(
                    onClick = { dropdownExpanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(viewModel.selectedImportance.getDisplayNameId()))
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    importances.forEach { importance ->
                        DropdownMenuItem(
                            text = { Text(stringResource(importance.getDisplayNameId())) },
                            onClick = {
                                viewModel.onImportanceChange(importance)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.openMainActivity))
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = viewModel.openMainActivity,
                    onCheckedChange = { viewModel.onOpenMainActivityChange(it)}
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.addReplyAction))
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = viewModel.replyActionEnabled,
                    onCheckedChange = { viewModel.onReplyActionEnabledChange(it)}
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (viewModel.title.isBlank()) {
                        viewModel.titleError = R.string.titleError
                    } else {
                        createNotification(context = context, viewModel = viewModel)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.create))
            }
        }

    }

}