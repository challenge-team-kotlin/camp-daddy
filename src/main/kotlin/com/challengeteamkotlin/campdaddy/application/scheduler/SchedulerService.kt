package com.challengeteamkotlin.campdaddy.application.scheduler

import com.challengeteamkotlin.campdaddy.application.chat.ChatMessageSendManager
import com.challengeteamkotlin.campdaddy.application.chat.ChatRoomService
import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventSubscriber
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.CreateChatRoomRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event.ReservationEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val reservationEventSubscriber: ReservationEventSubscriber,
    private val chatRoomService: ChatRoomService,
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
                    val chatRoomId = chatRoomService.createChat(CreateChatRoomRequest(this.buyerId, this.productId))
                    chatMessageSendManager.sendToChatRoom(chatRoomId, it)
                }

            reservationEventSubscriber.deleteMessage(it)
        }
    }
}
