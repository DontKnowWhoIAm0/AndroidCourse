package com.example.androidcourse.ui.screen

import android.app.NotificationManager
import android.content.Context
import android.service.notification.StatusBarNotification
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidcourse.R
import com.example.androidcourse.data.Notification
import com.example.androidcourse.data.NotificationIDsController
import com.example.androidcourse.util.createNotification

@Composable
fun EditScreen(
    viewModel: NotificationViewModel = viewModel(),
    navController: NavController,
    innerPadding: PaddingValues
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val density = LocalDensity.current
    val isKeyboardOpen = WindowInsets.ime.getBottom(density) > 0
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    var activeNotifications = listOf<StatusBarNotification>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                stringResource(R.string.second_screen_title),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = viewModel.editId,
                onValueChange = { viewModel.onEditIdChange(it) },
                label = { Text(stringResource(R.string.id)) },
                isError = viewModel.blankIdError != 0 || viewModel.notIntIdError != 0,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.blankIdError != 0) {
                Text(stringResource(R.string.blankIdError))
            } else if (viewModel.notIntIdError != 0) {
                Text(stringResource(R.string.notIntIdError))
            }

            OutlinedTextField(
                value = viewModel.editText,
                onValueChange = { viewModel.onEditTextChange(it) },
                label = { Text(stringResource(R.string.new_text)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
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
            Button(
                onClick = {
                    activeNotifications = notificationManager.activeNotifications.toList()
                    when {
                        viewModel.editId.isBlank() ->
                            viewModel.blankIdError = R.string.blankIdError

                        viewModel.editId.toIntOrNull() == null ->
                            viewModel.notIntIdError = R.string.notIntIdError

                        activeNotifications.any { it.id == viewModel.editId.toIntOrNull() } -> {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            val id = viewModel.editId.toIntOrNull()!!
                            val notificationObj = NotificationIDsController.get(id)!!
                            viewModel.blankIdError = 0
                            viewModel.notIntIdError = 0

                            val notification = Notification(
                                id,
                                notificationObj.title,
                                viewModel.editText,
                                notificationObj.importance,
                                notificationObj.expandable,
                                notificationObj.openMainActivity,
                                notificationObj.replyActionEnabled
                            )

                            NotificationIDsController.edit(notification)
                            createNotification(
                                context = context,
                                notification = notification
                            )

                            viewModel.onEditTextChange("")
                            viewModel.onEditIdChange("")
                        }

                        else -> {
                            Toast.makeText(
                                context,
                                context.getString(R.string.toast_msg_editing),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.edit))
            }

            Button(

                onClick = {
                    activeNotifications = notificationManager.activeNotifications.toList()
                    if (activeNotifications.isNotEmpty()) {
                        notificationManager.cancelAll()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_msg_deleting),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(stringResource(R.string.delete_all))
            }


        }

    }

}