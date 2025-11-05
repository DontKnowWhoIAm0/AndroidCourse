package com.example.androidcourse.data

object UserMessages {

    private val messages = mutableListOf<String>()

    fun addMessage(message: String) {
        messages.add(message)
    }

    fun getMessages(): List<String> = messages.toList()

}

