package com.aldev.chatbotgemini.repository

import android.util.Log
import com.aldev.chatbotgemini.BuildConfig
import com.aldev.chatbotgemini.data.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor() : ChatBotRepository {
    override suspend fun getTextResponseFromGemini(prompt: String): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.API_KEY
        )

        return try {
            val response = generativeModel.generateContent(prompt)

            Log.d("Gemini Response", "getResponse: ${response.text}")

            Chat(
                prompt = response.text ?: "error",
                isFromUser = false,
                isError = false
            )
        } catch (e: ResponseStoppedException) {
            Chat(
                prompt = e.message ?: "error",
                isFromUser = false,
                isError = true
            )
        }
    }
}