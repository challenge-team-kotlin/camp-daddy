package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat

import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse

interface ChatRoomQueryDslRepository {

    fun getPersonalChatList(memberId: Long): List<ChatRoomResponse>
}