package com.aldev.chatbotgemini.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldev.chatbotgemini.data.Chat
import com.aldev.chatbotgemini.databinding.ItemChatReceiverBinding
import com.aldev.chatbotgemini.databinding.ItemChatSenderBinding

class MainAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val chatList: MutableList<Chat> = mutableListOf()

    fun setListItem(chatList: List<Chat>) {
        this.chatList.clear()
        this.chatList.addAll(chatList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].isFromUser) {
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> SendViewHolder(
                ItemChatSenderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> ReceiverViewHolder(
                ItemChatReceiverBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = chatList[position]
        if (item.isFromUser) {
            (holder as? SendViewHolder)?.bindItem(item)
        } else {
            (holder as? ReceiverViewHolder)?.bindItem(item)
        }
    }
}