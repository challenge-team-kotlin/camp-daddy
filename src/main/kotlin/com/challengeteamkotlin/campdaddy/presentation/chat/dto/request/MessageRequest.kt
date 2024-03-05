package com.challengeteamkotlin.campdaddy.presentation.chat.dto.request

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatMessageEntity
import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.domain.model.chat.MessageStatus
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity

data class MessageRequest(
    val userId: Long,
    val message: String,
    val status: MessageStatus
) {
    fun of(member: MemberEntity, chatRoom: ChatRoomEntity) =
        ChatMessageEntity(message, member, chatRoom, status)

}
