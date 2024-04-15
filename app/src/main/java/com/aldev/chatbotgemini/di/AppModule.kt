package com.aldev.chatbotgemini.di

import com.aldev.chatbotgemini.repository.ChatBotRepository
import com.aldev.chatbotgemini.repository.ChatBotRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideChatBotRepository(chatBotRepositoryImpl: ChatBotRepositoryImpl): ChatBotRepository
}