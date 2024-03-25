package com.challengeteamkotlin.campdaddy.application.scheduler

import com.challengeteamkotlin.campdaddy.application.chat.ChatMessageSendManager
import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventSubscriber
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepository
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event.ReservationEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val reservationEventSubscriber: ReservationEventSubscriber,
    private val chatRoomRepository: ChatRoomRepository,
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
                    val chatRoom = chatRoomRepository.getChatRoomByBuyerIdAndProductId(this.buyerId, this.productId)
                        ?: throw EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)
                    chatMessageSendManager.sendToChatRoom(chatRoom.id!!, it)
                }

            reservationEventSubscriber.deleteMessage(it)
        }
    }
}