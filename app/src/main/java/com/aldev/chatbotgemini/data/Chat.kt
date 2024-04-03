package com.aldev.chatbotgemini.data

data class Chat(
    val prompt: String,
    val isFromUser: Boolean,
    val isError: Boolean
)
