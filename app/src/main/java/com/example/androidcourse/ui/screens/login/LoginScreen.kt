package com.example.androidcourse.ui.screens.login;

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.androidcourse.R
import com.example.androidcourse.data.NavigationKeys
import com.example.androidcourse.ui.theme.LocalAppColorScheme

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val colors = LocalAppColorScheme.current

    Scaffold(
        containerColor = colors.backgroundColor
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .imePadding()
                .navigationBarsPadding()
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(100.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                ) {
                    if (viewModel.blankError != 0) {
                        Text(
                            text = stringResource(viewModel.blankError),
                            color = colors.errorColor,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 4.dp, top = 4.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text(stringResource(R.string.emailLabel)) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.borderColor,
                        unfocusedBorderColor = colors.borderColor,
                        errorBorderColor = colors.errorColor,
                        cursorColor = colors.borderColor,
                        focusedLabelColor = colors.inputTextColor,
                        unfocusedLabelColor = colors.inputTextColor,
                        focusedTextColor = colors.inputTextColor,
                        unfocusedTextColor = colors.inputTextColor,
                        focusedContainerColor = colors.fieldColor,
                        unfocusedContainerColor = colors.fieldColor,
                        errorLabelColor = colors.errorColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                ) {
                    if (viewModel.emailError != 0) {
                        Text(
                            text = stringResource(viewModel.emailError),
                            fontSize = 14.sp,
                            color = colors.errorColor,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text(stringResource(R.string.passwordLabel)) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.borderColor,
                        unfocusedBorderColor = colors.borderColor,
                        errorBorderColor = colors.errorColor,
                        cursorColor = colors.borderColor,
                        focusedLabelColor = colors.inputTextColor,
                        unfocusedLabelColor = colors.inputTextColor,
                        focusedTextColor = colors.inputTextColor,
                        unfocusedTextColor = colors.inputTextColor,
                        focusedContainerColor = colors.fieldColor,
                        unfocusedContainerColor = colors.fieldColor
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (viewModel.passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff
                        val description =
                            if (viewModel.passwordVisible) stringResource(R.string.visibilityOffDesc)
                            else stringResource(R.string.visibilityOnDesc)
                        IconButton(onClick = { viewModel.onTogglePasswordVisibility() }) {
                            Icon(imageVector = image, contentDescription = description)
                        }
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                ) {
                    if (viewModel.passwordError != 0) {
                        Text(
                            text = stringResource(viewModel.passwordError),
                            color = colors.errorColor,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }

            Button(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    val isValidate = viewModel.validateFields()
                    if (isValidate) {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            NavigationKeys.EMAIL, viewModel.email
                        )
                        navController.navigate(NavigationKeys.HOME)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(stringResource(R.string.login), color = colors.buttonTextColor)
            }
        }
    }
}

