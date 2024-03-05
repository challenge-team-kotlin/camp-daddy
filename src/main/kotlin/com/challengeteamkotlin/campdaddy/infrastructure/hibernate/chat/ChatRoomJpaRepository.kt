package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomJpaRepository: JpaRepository<ChatRoomEntity, Long> {
    fun findByMemberIdAndProductId(memberId: Long, productId: Long): ChatRoomEntity?
}
