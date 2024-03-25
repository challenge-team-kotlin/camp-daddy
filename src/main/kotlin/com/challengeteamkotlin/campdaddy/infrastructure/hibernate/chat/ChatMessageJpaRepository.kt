package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageJpaRepository : JpaRepository<ChatMessageEntity, Long> {
    fun findFirstByChatRoomIdOrderByCreatedAt(roomId: Long): ChatMessageEntity
}