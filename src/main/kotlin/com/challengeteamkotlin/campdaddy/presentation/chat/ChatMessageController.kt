package com.challengeteamkotlin.campdaddy.presentation.chat

import com.challengeteamkotlin.campdaddy.application.chat.ChatMessageSendManager
import com.challengeteamkotlin.campdaddy.application.chat.ChatMessageService
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import jakarta.validation.Valid
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatMessageController(
    private val chatMessageService: ChatMessageService,
    private val chatMessageSendManager: ChatMessageSendManager
) {

    @MessageMapping("/chat/{roomId}")
    fun chat(
        @DestinationVariable roomId: Long,
        @Payload @Valid request: MessageRequest,
    ) {
        chatMessageSendManager.sendToChatRoom(roomId, request)
        chatMessageService.save(roomId, request)
    }

}
