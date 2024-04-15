package com.aldev.chatbotgemini.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldev.chatbotgemini.data.Chat
import com.aldev.chatbotgemini.databinding.ActivityMainBinding
import com.aldev.chatbotgemini.presentation.adapter.MainAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val mainAdapter by lazy { MainAdapter() }
    private val chatList: MutableList<Chat> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeLiveData()

        binding.ivSend.setOnClickListener {
            val inputText = binding.edtChat.text.toString()
            if (inputText.isNotBlank()) {
                viewModel.addChatList(Chat(inputText, true, false))
                mainAdapter.setListItem(viewModel.getChatList())
                binding.edtChat.text.clear()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvChat) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }

        mainAdapter.setListItem(chatList)
    }

    private fun observeLiveData() {
        with(viewModel) {
            getSuccessGeminiLiveData().observe(this@MainActivity) {
                mainAdapter.setListItem(it)
            }
            getErrorGeminiLiveData().observe(this@MainActivity) {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}