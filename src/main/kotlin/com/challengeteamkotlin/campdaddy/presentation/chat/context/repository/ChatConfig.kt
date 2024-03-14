package com.challengeteamkotlin.campdaddy.presentation.chat.context.repository

import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepositoryImpl
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepository
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepositoryImpl
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatMessageJpaRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatRoomJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatConfig {

    @Bean
    fun chatMessageRepository(jpaRepository: ChatMessageJpaRepository): ChatMessageRepository {
        return ChatMessageRepositoryImpl(jpaRepository)
    }

    @Bean
    fun chatRoomRepository(jpaRepository: ChatRoomJpaRepository): ChatRoomRepository {
        return ChatRoomRepositoryImpl(jpaRepository)
    }
}