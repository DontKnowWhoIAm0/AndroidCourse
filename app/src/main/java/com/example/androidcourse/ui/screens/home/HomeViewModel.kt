package com.example.androidcourse.ui.screens.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.androidcourse.data.Note
import com.example.androidcourse.data.NoteRepoImpl

class HomeViewModel : ViewModel() {

    val notes: SnapshotStateList<Note> = NoteRepoImpl.getAllNotes()

}