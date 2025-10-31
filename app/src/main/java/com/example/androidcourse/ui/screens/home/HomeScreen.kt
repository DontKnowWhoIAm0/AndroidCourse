package com.example.androidcourse.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidcourse.R
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import com.example.androidcourse.data.NavigationKeys
import com.example.androidcourse.data.Note
import com.example.androidcourse.ui.theme.LocalAppColorScheme
import com.example.androidcourse.ui.theme.ThemeEnum
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.sp
import com.example.androidcourse.data.NoteRepoImpl

@Composable
fun HomeScreen(
    navController: NavController,
    email: String,
    viewModel: HomeViewModel = viewModel(),
    onThemeChange: (ThemeEnum) -> Unit
) {
    val notes = viewModel.notes
    val colors = LocalAppColorScheme.current

    var dropdownExpanded by remember { mutableStateOf(false) }
    val dropdownOptions = ThemeEnum.entries
    val density = LocalDensity.current

    Scaffold(
        containerColor = colors.backgroundColor
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = email,
                color = colors.errorColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                var buttonWidth by remember { mutableStateOf(0.dp) }

                OutlinedButton(
                    onClick = { dropdownExpanded = true },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = colors.fieldColor,
                        contentColor = colors.inputTextColor
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = colors.borderColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            with(density) {
                                buttonWidth = coordinates.size.width.toDp()
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.dropdownButton),
                            fontSize = 14.sp
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.theme)
                        )
                    }
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    modifier = Modifier
                        .width(buttonWidth)
                        .background(color = colors.fieldColor)
                ) {
                    dropdownOptions.forEach { theme ->
                        DropdownMenuItem(
                            text = { Text(theme.displayName, color = colors.inputTextColor) },
                            onClick = {
                                dropdownExpanded = false
                                onThemeChange(theme)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (notes.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(stringResource(R.string.noNotes), color = colors.errorColor)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notes) { note: Note ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            colors = CardDefaults.cardColors(
                                containerColor = colors.fieldColor
                            ),
                            border = BorderStroke(1.dp, colors.borderColor)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = note.title,
                                    color = colors.inputTextColor,
                                    fontSize = 22.sp
                                )

                                if (note.text.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = note.text,
                                        color = colors.inputTextColor,
                                        fontSize = 18.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row (
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        IconButton(
                                            onClick = {
                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                    NavigationKeys.ID, note.id
                                                )
                                                navController.navigate(NavigationKeys.EDIT_NOTE)
                                            },
                                            modifier = Modifier
                                                .align(Alignment.Bottom)
                                                .size(30.dp)
                                                .padding(horizontal = 2.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = stringResource(R.string.editButton),
                                                tint = colors.inputTextColor
                                            )
                                        }

                                        IconButton(
                                            onClick = {
                                                NoteRepoImpl.deleteNote(note.id)
                                            },
                                            modifier = Modifier
                                                .align(Alignment.Bottom)
                                                .size(30.dp)
                                                .padding(horizontal = 2.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = stringResource(R.string.deleteButton),
                                                tint = colors.inputTextColor
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { navController.navigate(NavigationKeys.ADD_NOTE) },
                colors = ButtonDefaults.buttonColors(containerColor = colors.borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(R.string.addNote))
            }

        }
    }
}