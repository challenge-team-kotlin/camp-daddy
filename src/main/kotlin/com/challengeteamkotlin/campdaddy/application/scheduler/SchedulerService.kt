package com.challengeteamkotlin.campdaddy.application.scheduler

import com.challengeteamkotlin.campdaddy.application.chat.ChatMessageSendManager
import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.application.product.exception.ProductErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventSubscriber
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepository
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.CreateChatRoomRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event.ReservationEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val reservationEventSubscriber: ReservationEventSubscriber,
    private val chatRoomRepository: ChatRoomRepository,
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
    private val chatMessageSendManager: ChatMessageSendManager,
) {

    @Scheduled(fixedRateString = "5000")
    fun reservationEventListener() {
        val receiveMessages = reservationEventSubscriber.receiveMessages()
        receiveMessages.forEach {
            jacksonObjectMapper().readTree(it.body())["Message"]
                .let { jsonNode -> jacksonObjectMapper().readTree(jsonNode.asText()) }
                .let { jsonNode -> jacksonObjectMapper().treeToValue<ReservationEvent>(jsonNode) }
                .run {
                    val chatRoom =
                        when (this.changeStatus) {
                        ReservationStatus.REQ -> chatRoomRepository.getChatRoomByBuyerIdAndProductId(this.buyerId, this.productId) ?: throw EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)

                        else -> {
                            val createChatRoomRequest = CreateChatRoomRequest(this.buyerId, this.productId)

                            val buyer = memberRepository.getMemberByIdOrNull(this.buyerId) ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
                            val seller = memberRepository.getMemberByIdOrNull(this.sellerId) ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
                            val product = productRepository.getProductById(this.productId) ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

                            chatRoomRepository.getChatRoomByBuyerIdAndProductId(this.buyerId, this.productId) ?: chatRoomRepository.createChat(createChatRoomRequest.of(buyer, seller, product))
                        }
                    }

                    chatMessageSendManager.sendToChatRoom(chatRoom.id!!, it)
                }

            reservationEventSubscriber.deleteMessage(it)
        }
    }
}
