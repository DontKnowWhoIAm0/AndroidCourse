package com.example.androidcourse.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidcourse.data.Importance
import com.example.androidcourse.R
import com.example.androidcourse.util.createNotification
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import com.example.androidcourse.data.Notification
import com.example.androidcourse.data.NotificationIDsController

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = viewModel(),
    navController: NavController,
    innerPadding: PaddingValues
) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    val importances = Importance.entries
    val context = LocalContext.current
    val density = LocalDensity.current

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isKeyboardOpen = WindowInsets.ime.getBottom(density) > 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                stringResource(R.string.first_screen_title),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = viewModel.title,
                onValueChange = { viewModel.onTitleChange(it) },
                label = { Text(stringResource(R.string.title)) },
                isError = viewModel.titleError != 0,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.titleError != 0) {
                Text(stringResource(R.string.titleError))
            }

            OutlinedTextField(
                value = viewModel.text,
                onValueChange = { viewModel.onTextChange(it) },
                label = { Text(stringResource(R.string.text)) },
                modifier = Modifier.fillMaxWidth()
            )

            Box {
                var buttonWidth by remember { mutableStateOf(0.dp) }

                OutlinedButton(
                    onClick = { dropdownExpanded = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            with(density) {
                                buttonWidth = coordinates.size.width.toDp()
                            }
                        }
                ) {
                    Text(stringResource(viewModel.selectedImportance.getDisplayNameId()))
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier.width(buttonWidth)
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.expandNotification))
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = viewModel.expandable,
                    onCheckedChange = { viewModel.onExpandableChange(it) },
                    enabled = viewModel.text.isNotBlank()
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.openMainActivity))
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = viewModel.openMainActivity,
                    onCheckedChange = { viewModel.onOpenMainActivityChange(it) }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.addReplyAction))
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = viewModel.replyActionEnabled,
                    onCheckedChange = { viewModel.onReplyActionEnabledChange(it) }
                )
            }
        }

        Button(
            onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()

                if (viewModel.title.isBlank()) {
                    viewModel.titleError = R.string.titleError
                } else {
                    val notification = Notification(
                        NotificationIDsController.getId(),
                        viewModel.title,
                        viewModel.text,
                        viewModel.selectedImportance,
                        viewModel.expandable,
                        viewModel.openMainActivity,
                        viewModel.replyActionEnabled,
                    )
                    NotificationIDsController.add(notification)
                    createNotification(
                        context = context,
                        notification = notification
                    )
                    viewModel.onTitleChange("")
                    viewModel.onTextChange("")
                    viewModel.onExpandableChange(false)
                    viewModel.onOpenMainActivityChange(false)
                    viewModel.onReplyActionEnabledChange(false)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .imePadding()
                .then(
                    if (!isKeyboardOpen)
                        Modifier.padding(innerPadding)
                    else
                        Modifier
                )
        ) {
            Text(stringResource(R.string.create))
        }

    }


}