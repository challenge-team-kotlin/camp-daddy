package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepository
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatMessageService(
    private val memberRepository: MemberRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
) {

    fun getChatMessages(roomId: Long): List<MessageResponse>? {
        return chatMessageRepository.findByChatRoomId(roomId)?.map {
            MessageResponse.from(it)
        } ?: emptyList()
    }

    fun save(roomId: Long, request: MessageRequest) {
        val sender = memberRepository.findByIdOrNull(request.userId) ?: TODO("throw EntityNotFoundException()")
        val chatRoom = chatRoomRepository.findByIdOrNull(roomId) ?: TODO("throw EntityNotFoundException()")

        val message = request.of(
            message = request.message,
            member = sender,
            chatRoom = chatRoom,
            status = request.status
        )

        chatMessageRepository.save(message)
    }
}