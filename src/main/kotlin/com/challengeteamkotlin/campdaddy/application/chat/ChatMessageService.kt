package com.challengeteamkotlin.campdaddy.application.chat

import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatErrorCode
import com.challengeteamkotlin.campdaddy.application.chat.exception.ChatFailureException
import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
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
        val sender = memberRepository.findByIdOrNull(request.userId) ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
        val chatRoom = chatRoomRepository.getChatRoomById(roomId) ?: throw EntityNotFoundException(ChatErrorCode.CHAT_NOT_FOUND)

        if (chatRoom.buyer.id != sender.id && chatRoom.seller.id != sender.id) {
            throw ChatFailureException(ChatErrorCode.ACCESS_DENIED)
        }


        val message = chatMessageRepository.createChatMessage(request.of(sender, chatRoom))

        return message.id!!
    }

}
