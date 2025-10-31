package com.example.androidcourse.ui.screens.addnote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidcourse.R

class AddNoteViewModel : ViewModel() {

    var title by mutableStateOf("")
        private set

    var text by mutableStateOf("")
        private set

    var blankTitle by mutableStateOf(0)
        private set

    fun onTitleChange(newTitle: String) {
        title = newTitle
        if (blankTitle != 0) {blankTitle = 0}
    }

    fun onTextChange(newText: String) {
        text = newText
    }

    fun validateField(): Boolean {
        var isValidate = true

        if (title.isBlank()) {
            blankTitle = R.string.blankTitle
            isValidate = false
        }

        return isValidate
    }
}