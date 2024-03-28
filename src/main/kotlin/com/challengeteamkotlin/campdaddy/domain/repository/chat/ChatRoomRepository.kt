package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse

interface ChatRoomRepository {
    fun createChat(chatRoom: ChatRoomEntity): ChatRoomEntity
    fun getChatRoomById(roomId: Long): ChatRoomEntity?
    fun getChatRoomByBuyerIdAndProductId(buyerId: Long, productId: Long): ChatRoomEntity?
    fun getPersonalChatList(memberId: Long): List<ChatRoomResponse>
    fun removeChat(chatRoom: ChatRoomEntity)
}
