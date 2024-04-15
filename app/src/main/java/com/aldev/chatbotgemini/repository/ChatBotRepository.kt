package com.aldev.chatbotgemini.repository

import com.aldev.chatbotgemini.data.Chat

interface ChatBotRepository {
    suspend fun getTextResponseFromGemini(prompt: String): Chat
}