package com.example.androidcourse.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.example.androidcourse.data.Importance
import com.example.androidcourse.data.UserMessages.addMessage

class NotificationViewModel : ViewModel() {
    var title by mutableStateOf("")
    var titleError by mutableStateOf(0)
    var text by mutableStateOf("")
    var expandable by mutableStateOf(false)
    var selectedImportance by mutableStateOf(Importance.MEDIUM)
    var openMainActivity by mutableStateOf(false)
    var replyActionEnabled by mutableStateOf(false)

    var editId by mutableStateOf("")
    var editText by mutableStateOf("")
    var blankIdError by mutableStateOf(0)
    var notIntIdError by mutableStateOf(0)

    var userMessage by mutableStateOf("")
    var blankMessageError by mutableStateOf(0)
    var userMessages by mutableStateOf(listOf<String>())

    fun onTitleChange(newTitle: String) {
        title = newTitle
        if (titleError != 0) {titleError = 0}
    }

    fun onTextChange(newText: String) {
        text = newText
        if (newText.isBlank()) {
            expandable = false
        }
    }

    fun onExpandableChange(isExpanded: Boolean) {
        if (text.isNotBlank()) {
            expandable = isExpanded
        } else {
            expandable = false
        }
    }

    fun onImportanceChange(importance: Importance) {
        selectedImportance = importance
    }

    fun onOpenMainActivityChange(enabled: Boolean) {
        openMainActivity = enabled
    }

    fun onReplyActionEnabledChange(enabled: Boolean) {
        replyActionEnabled = enabled
    }

    fun onEditIdChange(newId: String) {
        editId = newId
        if (blankIdError != 0) {blankIdError = 0}
        if (notIntIdError != 0 && newId.toIntOrNull() != null) {notIntIdError = 0}
    }

    fun onEditTextChange(newText: String) {
        editText = newText
    }

    fun onUserMessageInputChange(newText: String) {
        userMessage = newText
        if (blankMessageError != 0) {blankMessageError = 0}
    }

    fun addUserMessage() {
        if (userMessage.isNotBlank()) {
            addMessage(userMessage)
            userMessage = ""
        }
    }
}