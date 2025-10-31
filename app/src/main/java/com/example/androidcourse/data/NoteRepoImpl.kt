package com.example.androidcourse.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object NoteRepoImpl : NoteRepo {

    private val notes = mutableStateListOf<Note>()
    private var nextId = 0;

    override fun addNote(title: String, text: String) {
        notes.add(Note(nextId++, title, text))
    }

    override fun editNote(id: Int, newTitle: String, newText: String) {
        val index = notes.indexOfFirst { it.id == id }
        if (index != -1) {
            notes[index] = notes[index].copy(title = newTitle, text = newText)
        }
    }

    override fun deleteNote(id: Int) {
        val index = notes.indexOfFirst { it.id == id }
        if (index != -1) {
            notes.removeAt(index)
        }
    }

    override fun getAllNotes(): SnapshotStateList<Note> = notes

    fun getNoteById(id: Int): Note? { return notes.find { it.id == id } }
}