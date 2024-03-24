package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.QChatMessageEntity
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.MessageResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl

class ChatMessageQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ChatMessageQueryDslRepository {

    private val chatMessage = QChatMessageEntity.chatMessageEntity

    override fun getChatMessageByChatRoomId(roomId: Long, page: Pageable): Slice<MessageResponse> {
        val response = queryFactory.select(
            Projections.constructor(
                MessageResponse::class.java,
                chatMessage.member.nickname,
                chatMessage.message,
                chatMessage.createdAt
            )
        ).from(chatMessage)
            .where(chatMessage.id.lt(roomId))
            .orderBy(chatMessage.createdAt.desc())
            .limit(page.pageSize.toLong() + 1)
            .fetch()

        return checkLastPage(response, page)
    }

    // 마지막 페이지 확인
    private fun checkLastPage(response: MutableList<MessageResponse>, page: Pageable): Slice<MessageResponse> {

        var hasNext = false

        if (response.size > page.pageSize) {
            response.removeAt(response.size - 1)
            hasNext = true
        }

        return SliceImpl(response, page, hasNext)
    }
}