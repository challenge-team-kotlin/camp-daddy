package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.application.product.exception.ProductErrorCode
import com.challengeteamkotlin.campdaddy.common.auth.isAuthorized
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.domain.model.chat.ChatRoomEntity
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepository
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.CreateChatRoomRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomDetailResponse
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
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
        val buyer = memberRepository.getMemberByIdOrNull(request.buyerId) ?: throw EntityNotFoundException(
            MemberErrorCode.MEMBER_NOT_FOUND
        )
        val product = productRepository.getProductById(request.productId) ?: throw EntityNotFoundException(
            ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION
        )

        return when (val chatRoom = chatRoomRepository.getChatRoomByBuyerIdAndProductId(request.buyerId, request.productId)) {
            null -> chatRoomRepository.createChat(request.of(buyer, product.member, product)).id!!
            else -> chatRoom.id!!
        }
    }

    @Transactional(readOnly = true)
    fun getPersonalChatList(memberId: Long, userPrincipal: UserPrincipal): List<ChatRoomResponse>? {
        return isAuthorized(memberId, userPrincipal) {
            chatRoomRepository.getPersonalChatList(memberId)
        }
    }

    @Transactional(readOnly = true)
    fun getChatDetail(roomId: Long, userPrincipal: UserPrincipal): ChatRoomDetailResponse {
        val chatRoom = getChatRoom(roomId)

        return isAuthorized(userPrincipal.id, chatRoom.seller.id!!, chatRoom.buyer.id!!) {
            val chatMessages = chatMessageRepository.getChatMessageByChatRoomId(chatRoom.id!!)?.map {
                MessageResponse.from(it)
            } ?: emptyList()

            ChatRoomDetailResponse.of(chatRoom, chatMessages)
        }
    }

    @Transactional
    fun removeChat(roomId: Long, userPrincipal: UserPrincipal) {
        val chatRoom = getChatRoom(roomId)

        return isAuthorized(userPrincipal.id, chatRoom.seller.id!!, chatRoom.buyer.id!!) {
            chatRoomRepository.removeChat(chatRoom)
        }
    }

    private fun getChatRoom(roomId: Long): ChatRoomEntity {
        return chatRoomRepository.getChatRoomById(roomId) ?: throw EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)
    }
}
