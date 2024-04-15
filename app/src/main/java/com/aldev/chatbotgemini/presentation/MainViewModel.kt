package com.aldev.chatbotgemini.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldev.chatbotgemini.data.Chat
import com.aldev.chatbotgemini.repository.ChatBotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ChatBotRepository
): ViewModel() {

    private val chatList = mutableListOf<Chat>()
    private val successGeminiLiveData: MutableLiveData<List<Chat>> = MutableLiveData()
    private val errorGeminiLiveData: MutableLiveData<String> = MutableLiveData()

    fun addChatList(chat: Chat) {
        chatList.add(chat)
        sendQuestion(chat.prompt)
    }

    fun getChatList(): List<Chat> {
        return chatList
    }

    fun getSuccessGeminiLiveData(): LiveData<List<Chat>> {
        return successGeminiLiveData
    }

    fun getErrorGeminiLiveData(): LiveData<String> {
        return errorGeminiLiveData
    }

    private fun sendQuestion(prompt: String) {
        viewModelScope.launch {
            val chatResponse = repository.getTextResponseFromGemini(prompt)
            if (chatResponse.isError) {
                errorGeminiLiveData.value = chatResponse.prompt
            } else {
                chatList.add(chatResponse)
                successGeminiLiveData.value = chatList
            }
        }
    }
}