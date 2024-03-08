package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatFailureException
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.common.exception.code.CommonErrorCode
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatMessageRepository
import com.challengeteamkotlin.campdaddy.domain.repository.chat.ChatRoomRepository
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.request.MessageRequest
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatMessageService(
    private val memberRepository: MemberRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
) {

    @Transactional
    fun save(roomId: Long, request: MessageRequest): Long {
        val sender = memberRepository.findByIdOrNull(request.userId) ?: throw EntityNotFoundException(CommonErrorCode.ID_NOT_FOUND)
        val chatRoom = chatRoomRepository.findByIdOrNull(roomId) ?: throw EntityNotFoundException(CommonErrorCode.ID_NOT_FOUND)

        if (chatRoom.buyer != sender && chatRoom.seller != sender) {
            throw throw ChatFailureException(ChatErrorCode.ACCESS_DENIED)
        }

        val message = request.of(sender, chatRoom)

        chatMessageRepository.save(message)

        return message.id!!
    }

}