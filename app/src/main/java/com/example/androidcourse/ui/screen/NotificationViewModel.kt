package com.example.androidcourse.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

import com.example.androidcourse.data.Importance

class NotificationViewModel : ViewModel() {
    var title by mutableStateOf("")
    var titleError by mutableStateOf(0)
    var text by mutableStateOf("")
    var expandable by mutableStateOf(false)
    var selectedImportance by mutableStateOf(Importance.MEDIUM)
    var openMainActivity by mutableStateOf(false)
    var replyActionEnabled by mutableStateOf(false)

    var editId by mutableStateOf(0)
    var editText by mutableStateOf("")

    var userMessageInput by mutableStateOf("")
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

    fun onEditIdChange(newId: Int) {
        editId = newId
    }

    fun onEditTextChange(newText: String) {
        editText = newText
    }

    fun onUserMessageInputChange(newText: String) {
        userMessageInput = newText
    }

    fun submitUserMessage() {
        if (userMessageInput.isNotBlank()) {
            addUserMessage(userMessageInput)
            userMessageInput = ""
        }
    }

    fun addUserMessage(message: String) {
        userMessages = userMessages + message
        //UserMessages.addMessage(message)
    }

    fun clearUserMessages() {
        userMessages = emptyList()
        //UserMessages.clearMessages()
    }
}