package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatRoomJpaRepository
import org.springframework.data.repository.findByIdOrNull

class ChatRoomRepositoryImpl(
    private val chatRoomJpaRepository: ChatRoomJpaRepository
) : ChatRoomRepository {

    override fun createChat(chatRoom: ChatRoomEntity): ChatRoomEntity {
        return chatRoomJpaRepository.save(chatRoom)
    }

    override fun getChatRoomById(roomId: Long): ChatRoomEntity? {
        return chatRoomJpaRepository.findByIdOrNull(roomId)
    }

    override fun getChatRoomByBuyerIdAndProductId(buyerId: Long, productId: Long): ChatRoomEntity? {
        return chatRoomJpaRepository.findByBuyerIdAndProductId(buyerId, productId)
    }

    override fun getChatRoomByBuyerIdOrSellerId(buyerId: Long, sellerId: Long): List<ChatRoomEntity>? {
        return chatRoomJpaRepository.findByBuyerIdOrSellerId(buyerId, sellerId)
    }

    override fun removeChat(chatRoom: ChatRoomEntity) {
        chatRoomJpaRepository.delete(chatRoom)
    }
}
