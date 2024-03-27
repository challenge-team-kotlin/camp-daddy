package com.challengeteamkotlin.campdaddy.domain.repository.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatRoomJpaRepository
import com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat.ChatRoomQueryDslRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse
import org.springframework.data.repository.findByIdOrNull

class ChatRoomRepositoryImpl(
    private val chatRoomJpaRepository: ChatRoomJpaRepository,
    private val chatRoomQueryDslRepository: ChatRoomQueryDslRepository
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

    override fun getPersonalChatList(memberId: Long): List<ChatRoomResponse> {
        return chatRoomQueryDslRepository.getPersonalChatList(memberId)
    }

    override fun removeChat(chatRoom: ChatRoomEntity) {
        chatRoomJpaRepository.delete(chatRoom)
    }
}
