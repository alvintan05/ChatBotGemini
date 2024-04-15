package com.aldev.chatbotgemini.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.aldev.chatbotgemini.data.Chat
import com.aldev.chatbotgemini.databinding.ItemChatSenderBinding

class SendViewHolder(private val binding: ItemChatSenderBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindItem(item: Chat) {
        binding.tvSender.text = item.prompt
    }
}