package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.MessageStatus
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event.ReservationEvent
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component

@Component
class ChatMessageSendManager(
    private val simpMessageTemplate: SimpMessageSendingOperations,
) {

    private final val ADMIN_ID = 9999L
    private final val SUBSCRIBE_DESTINATION = "/sub/chat"

    fun sendToChatRoom(roomId: Long, request: Any) {
        when (request) {
            is ReservationEvent -> {
                val messageRequest = MessageRequest(
                    userId = ADMIN_ID,
                    message = "예약 상태가 ${request.changeStatus.value}(으)로 변경되었습니다.",
                    status = MessageStatus.NOTICE
                )

                simpMessageTemplate.convertAndSend("$SUBSCRIBE_DESTINATION/$roomId", messageRequest)
            }

            is MessageRequest -> {
                simpMessageTemplate.convertAndSend("$SUBSCRIBE_DESTINATION/$roomId", request)
            }

        }
    }

}
