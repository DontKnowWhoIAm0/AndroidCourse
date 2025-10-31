package com.example.androidcourse.data

import androidx.compose.runtime.snapshots.SnapshotStateList

interface NoteRepo {
    fun addNote(title: String, text: String)
    fun getAllNotes(): SnapshotStateList<Note>
    fun editNote(id: Int, newTitle: String, newText: String)
    fun deleteNote(id: Int)
}