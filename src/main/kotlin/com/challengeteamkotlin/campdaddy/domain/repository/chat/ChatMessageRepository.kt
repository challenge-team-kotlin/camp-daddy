package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface ChatMessageRepository {
    fun createChatMessage(chatMessageEntity: ChatMessageEntity): ChatMessageEntity
    fun getLatestChatMessage(chatRoomId: Long): ChatMessageEntity
    fun getChatMessageByChatRoomId(chatRoomId: Long, page: Pageable): Slice<MessageResponse>
}
