package com.aldev.chatbotgemini.presentation

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
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

        setupView()
        setupRecyclerView()
        observeLiveData()
    }

    private fun setupView() {
        with(binding) {
            edtChat.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    ivSend.isVisible = (p0?.isNotBlank() == true)
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
            ivSend.setOnClickListener {
                val inputText = edtChat.text.toString()
                viewModel.addChatList(Chat(inputText, true, false))
                mainAdapter.setListItem(viewModel.getChatList())
                edtChat.text.clear()
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
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