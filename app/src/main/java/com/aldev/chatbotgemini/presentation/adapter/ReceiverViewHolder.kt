package com.aldev.chatbotgemini.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.aldev.chatbotgemini.data.Chat
import com.aldev.chatbotgemini.databinding.ItemChatReceiverBinding

class ReceiverViewHolder(private val binding: ItemChatReceiverBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindItem(item: Chat) {
        binding.tvReceiver.text = item.prompt
    }
}