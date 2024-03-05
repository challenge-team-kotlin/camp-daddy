package com.challengeteamkotlin.campdaddy.presentation.chat

import com.challengeteamkotlin.campdaddy.application.chat.ChatMessageService
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatMessageController(
    private val chatMessageService: ChatMessageService,
    private val simpMessageTemplate: SimpMessageSendingOperations
) {
    private final val SUBSCRIBE_DESTINATION = "/sub/chat/"

    @MessageMapping("/chat/{roomId}")
    fun chat(
        @DestinationVariable roomId: Long,
        @Payload @Valid request: MessageRequest
    ) {
        simpMessageTemplate.convertAndSend("$SUBSCRIBE_DESTINATION$roomId", request)
        // 메세지 저장 기능 추후 적용
    }

    @GetMapping("/chats/{roomId}/messages")
    fun getChatMessages(@PathVariable roomId: Long): ResponseEntity<List<MessageResponse>> {
        val messages = chatMessageService.getChatMessages(roomId)

        return ResponseEntity.ok().body(messages)
    }
}
