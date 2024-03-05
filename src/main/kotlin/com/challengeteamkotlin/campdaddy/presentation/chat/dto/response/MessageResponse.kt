package com.challengeteamkotlin.campdaddy.presentation.chat.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import java.time.LocalDateTime

data class MessageResponse(
    val nickname: String,
    val message: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(messageEntity: ChatMessageEntity) = MessageResponse(
            nickname = messageEntity.member.nickname,
            message = messageEntity.message,
            createdAt = messageEntity.createdAt
        )
    }
}
