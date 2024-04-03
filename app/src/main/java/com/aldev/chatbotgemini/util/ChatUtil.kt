package com.aldev.chatbotgemini.util

import android.util.Log
import com.aldev.chatbotgemini.data.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatUtil {
    const val API_KEY = "AIzaSyBBfiat6ZwetLQD5KRugHpdPidj-IJf7bs"

    suspend fun getResponse(prompt: String): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = API_KEY
        )

        return try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

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