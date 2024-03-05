package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ChatMessageJpaRepository : JpaRepository<ChatMessageEntity, Long> {
    fun findByChatRoomId(roomId: Long): List<ChatMessageEntity>?
}