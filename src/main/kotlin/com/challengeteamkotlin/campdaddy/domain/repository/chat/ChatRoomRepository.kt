package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity

interface ChatRoomRepository {
    fun createChat(chatRoom: ChatRoomEntity): ChatRoomEntity
    fun getChatRoomById(roomId: Long): ChatRoomEntity?
    fun getChatRoomByBuyerIdAndProductId(buyerId: Long, productId: Long): ChatRoomEntity?
    fun getChatRoomByBuyerIdOrSellerId(buyerId: Long, sellerId: Long): List<ChatRoomEntity>?
    fun removeChat(chatRoom: ChatRoomEntity)
}
