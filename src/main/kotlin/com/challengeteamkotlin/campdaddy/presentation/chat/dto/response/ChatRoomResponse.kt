package com.challengeteamkotlin.campdaddy.presentation.chat.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import java.time.LocalDateTime

data class ChatRoomResponse(
    val nickname: String,
    val productImageUrl: String?,
    val lastChatMessage: String?,
    val lastChatDate: LocalDateTime?,
) {
    companion object {
        fun of(member: MemberEntity, product: ProductEntity, message: ChatMessageEntity?) =
            ChatRoomResponse(
                nickname = member.nickname,
                productImageUrl = product.images[0]?.imageUrl,
                lastChatMessage = message?.message,
                lastChatDate = message?.createdAt
            )
    }
}
