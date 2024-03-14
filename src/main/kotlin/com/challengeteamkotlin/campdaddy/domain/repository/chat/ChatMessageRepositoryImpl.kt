package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatMessageJpaRepository

class ChatMessageRepositoryImpl(
    private val chatMessageJpaRepository: ChatMessageJpaRepository,
) : ChatMessageRepository {

    override fun createChatMessage(chatMessageEntity: ChatMessageEntity): ChatMessageEntity {
        return chatMessageJpaRepository.save(chatMessageEntity)
    }

    override fun getLatestChatMessage(chatRoomId: Long): ChatMessageEntity {
        return chatMessageJpaRepository.findFirstByChatRoomIdOrderByCreatedAt(chatRoomId)
    }

    override fun getChatMessageByChatRoomId(chatRoomId: Long): List<ChatMessageEntity>? {
        return chatMessageJpaRepository.findByChatRoomId(chatRoomId)
    }
}