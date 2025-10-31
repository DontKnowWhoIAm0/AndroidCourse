package com.example.androidcourse.ui.screens.addnote

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidcourse.R
import com.example.androidcourse.data.NoteRepoImpl
import com.example.androidcourse.ui.theme.LocalAppColorScheme

@Composable
fun AddNoteScreen(
    navController: NavController,
    noteId: Int? = null,
    viewModel: AddNoteViewModel = viewModel()
) {
    val colors = LocalAppColorScheme.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (noteId != null) {
        val note = NoteRepoImpl.getNoteById(noteId)
        LaunchedEffect(noteId) {
            if (note != null) {
                viewModel.onTitleChange(note.title)
                viewModel.onTextChange(note.text)
            }
        }
    }

    Scaffold (
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
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                ) {
                    if (viewModel.blankTitle != 0) {
                        Text(
                            text = stringResource(viewModel.blankTitle),
                            color = colors.errorColor,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(start = 4.dp, top = 4.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                OutlinedTextField(
                    value = viewModel.title,
                    onValueChange = { viewModel.onTitleChange(it) },
                    label = { Text(stringResource(R.string.titleLabel)) },
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
                        errorLabelColor = colors.errorColor,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.text,
                    onValueChange = { viewModel.onTextChange(it) },
                    label = { Text(stringResource(R.string.textLabel)) },
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
                        errorLabelColor = colors.errorColor,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            Button(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (viewModel.validateField()) {
                        if (noteId == null) {
                            NoteRepoImpl.addNote(viewModel.title, viewModel.text)
                        } else {
                            NoteRepoImpl.editNote(noteId, viewModel.title, viewModel.text)
                        }
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = if (noteId == null)
                    stringResource(R.string.saveNote)
                else
                    stringResource(R.string.editNote))
            }
        }
    }

}