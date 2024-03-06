package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepository
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.CreateChatRoomRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomDetailResponse
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun createChat(request: CreateChatRoomRequest): Long {
        return when (val chatRoom = chatRoomRepository.findByBuyerIdAndProductId(request.buyerId, request.productId)) {
            null -> {
                val buyer = memberRepository.findByIdOrNull(request.buyerId) ?: TODO("throw EntityNotFoundException()")
                val product =
                    productRepository.findByIdOrNull(request.productId) ?: TODO("throw EntityNotFoundException()")
                val chatRoom = request.of(buyer, product.member, product)

                chatRoomRepository.save(chatRoom).id!!
            }

            else -> chatRoom.id!!
        }
    }

    fun getPersonalChatList(memberId: Long): List<ChatRoomResponse>? {
        val member = memberRepository.findByIdOrNull(memberId) ?: TODO("throw EntityNotFoundException()")

        return chatRoomRepository.findByBuyerIdOrSellerId(member.id!!, member.id!!)?.map {
            val latestChat = chatMessageRepository.findFirstByChatRoomIdOrderByCreatedAt(it.id!!)

            when (it.buyer) {
                member -> ChatRoomResponse.of(it.seller, it.product, latestChat)
                else -> ChatRoomResponse.of(it.buyer, it.product, latestChat)
            }
        }
    }

    fun getChat(roomId: Long): ChatRoomDetailResponse {
        val chatRoom = chatRoomRepository.findByIdOrNull(roomId) ?: throw EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)
        val chatMessages = chatMessageRepository.findByChatRoomId(chatRoom.id!!)?.map {
            MessageResponse.from(it)
        } ?: emptyList()

        return ChatRoomDetailResponse.of(chatRoom, chatMessages)
    }

    @Transactional
    fun removeChat(roomId: Long) {
        val chatRoom = getChatRoom(roomId)

        chatRoomRepository.delete(chatRoom)
    }

    private fun getChatRoom(roomId: Long): ChatRoomEntity {
        return chatRoomRepository.findByIdOrNull(roomId) ?: throw EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)
    }
}
