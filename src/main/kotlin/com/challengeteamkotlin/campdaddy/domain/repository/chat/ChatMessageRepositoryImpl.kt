package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatMessageJpaRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatMessageQueryDslRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

class ChatMessageRepositoryImpl(
    private val chatMessageJpaRepository: ChatMessageJpaRepository,
    private val chatMessageQueryDslRepository: ChatMessageQueryDslRepository,
) : ChatMessageRepository {

    override fun createChatMessage(chatMessageEntity: ChatMessageEntity): ChatMessageEntity {
        return chatMessageJpaRepository.save(chatMessageEntity)
    }

    override fun getLatestChatMessage(chatRoomId: Long): ChatMessageEntity {
        return chatMessageJpaRepository.findFirstByChatRoomIdOrderByCreatedAt(chatRoomId)
    }

    override fun getChatMessageByChatRoomId(chatRoomId: Long, page: Pageable): Slice<MessageResponse> {
        return chatMessageQueryDslRepository.getChatMessageByChatRoomId(chatRoomId, page)
    }
}