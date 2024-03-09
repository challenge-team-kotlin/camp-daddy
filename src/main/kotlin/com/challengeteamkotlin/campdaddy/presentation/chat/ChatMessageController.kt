package com.challengeteamkotlin.campdaddy.presentation.chat

import com.challengeteamkotlin.campdaddy.application.chat.ChatMessageService
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import jakarta.validation.Valid
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatMessageController(
    private val chatMessageService: ChatMessageService,
    private val simpMessageTemplate: SimpMessageSendingOperations,
) {
    private final val SUBSCRIBE_DESTINATION = "/sub/chat/"

    @MessageMapping("/chat/{roomId}")
    fun chat(
        @DestinationVariable roomId: Long,
        @Payload @Valid request: MessageRequest
    ) {
        simpMessageTemplate.convertAndSend("$SUBSCRIBE_DESTINATION$roomId", request)
        chatMessageService.save(roomId, request)
    }

}
