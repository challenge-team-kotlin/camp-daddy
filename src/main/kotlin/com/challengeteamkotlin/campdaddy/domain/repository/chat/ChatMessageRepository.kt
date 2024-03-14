package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity

interface ChatMessageRepository {
    fun createChatMessage(chatMessageEntity: ChatMessageEntity): ChatMessageEntity
    fun getLatestChatMessage(chatRoomId: Long): ChatMessageEntity
    fun getChatMessageByChatRoomId(chatRoomId: Long): List<ChatMessageEntity>?
}
