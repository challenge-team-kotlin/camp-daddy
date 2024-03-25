package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat

import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Repository

@Repository
interface ChatMessageQueryDslRepository {

    fun getChatMessageByChatRoomId(roomId: Long, page: Pageable): Slice<MessageResponse>
}