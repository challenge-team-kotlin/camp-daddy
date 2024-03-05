package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import org.springframework.stereotype.Service

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
) {

    fun getChatMessages(roomId: Long): List<MessageResponse>? {
        return chatMessageRepository.findByChatRoomId(roomId)?.map {
            MessageResponse.from(it)
        } ?: emptyList()
    }

    // TODO: 메세지 저장 기능 추후 적용
}