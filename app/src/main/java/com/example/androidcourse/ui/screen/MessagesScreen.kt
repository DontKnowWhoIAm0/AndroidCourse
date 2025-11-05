package com.example.androidcourse.ui.screen

import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.androidcourse.data.UserMessages
import androidx.compose.ui.graphics.*


@Composable
fun MessagesScreen(
    viewModel: NotificationViewModel = viewModel(),
    navController: NavController,
    innerPadding: PaddingValues
) {

    val context = LocalContext.current
    val density = LocalDensity.current

    viewModel.userMessages = UserMessages.getMessages()

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
                stringResource(R.string.third_screen_title),
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = viewModel.userMessage,
                onValueChange = { viewModel.onUserMessageInputChange(it) },
                label = { Text(stringResource(R.string.user_message)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.blankMessageError != 0) {
                Text(stringResource(R.string.blank_message_error))
            }

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(viewModel.userMessages) { message ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = message,
                            fontSize = 14.sp
                        )
                    }
                }
            }

        }

        Button(
            onClick = {
                keyboardController?.hide()
                focusManager.clearFocus()

                if (viewModel.userMessage.isBlank()) {
                    viewModel.blankMessageError = R.string.blank_message_error
                } else {
                    viewModel.addUserMessage()
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
            Text(stringResource(R.string.add))
        }

    }

}