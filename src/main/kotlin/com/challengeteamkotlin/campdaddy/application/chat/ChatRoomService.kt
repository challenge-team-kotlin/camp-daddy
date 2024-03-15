package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.application.product.exception.ProductErrorCode
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
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun createChat(request: CreateChatRoomRequest): Long {
        val buyer = memberRepository.getMemberByIdOrNull(request.buyerId) ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
        val product = productRepository.findByIdOrNull(request.productId) ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

        return when (val chatRoom = chatRoomRepository.getChatRoomByBuyerIdAndProductId(request.buyerId, request.productId)) {
            null -> chatRoomRepository.createChat(request.of(buyer, product.member, product)).id!!
            else -> chatRoom.id!!
        }
    }

    @Transactional(readOnly = true)
    fun getPersonalChatList(memberId: Long): List<ChatRoomResponse>? {
        val member = memberRepository.getMemberByIdOrNull(memberId) ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)

        return chatRoomRepository.getChatRoomByBuyerIdOrSellerId(member.id!!, member.id!!)?.map {
            val latestChat = chatMessageRepository.getLatestChatMessage(it.id!!)

            when (it.buyer) {
                member -> ChatRoomResponse.of(it.seller, it.product, latestChat)
                else -> ChatRoomResponse.of(it.buyer, it.product, latestChat)
            }
        } ?: emptyList()
    }

    @Transactional(readOnly = true)
    fun getChatDetail(roomId: Long): ChatRoomDetailResponse {
        val chatRoom = getChatRoom(roomId)
        val chatMessages = chatMessageRepository.getChatMessageByChatRoomId(chatRoom.id!!)?.map {
            MessageResponse.from(it)
        } ?: emptyList()

        return ChatRoomDetailResponse.of(chatRoom, chatMessages)
    }

    @Transactional
    fun removeChat(roomId: Long) {
        val chatRoom = getChatRoom(roomId)

        chatRoomRepository.removeChat(chatRoom)
    }

    private fun getChatRoom(roomId: Long): ChatRoomEntity {
        return chatRoomRepository.getChatRoomById(roomId) ?: throw EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)
    }
}
