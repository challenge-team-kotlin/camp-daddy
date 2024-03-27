package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.chat

import com.challengeteamkotlin.campdaddy.domain.model.chat.QChatMessageEntity
import com.challengeteamkotlin.campdaddy.domain.model.chat.QChatRoomEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.QMemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.QProductImageEntity
import com.challengeteamkotlin.campdaddy.presentation.chat.dto.response.ChatRoomResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ChatRoomQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ChatRoomQueryDslRepository {

    private val chatRoom = QChatRoomEntity.chatRoomEntity
    private val member = QMemberEntity.memberEntity
    private val productImage = QProductImageEntity.productImageEntity
    private val chatMessage = QChatMessageEntity.chatMessageEntity

    override fun getPersonalChatList(memberId: Long): List<ChatRoomResponse> {
        return queryFactory.select(
            Projections.constructor(
                ChatRoomResponse::class.java,
                member.nickname,
                productImage.imageUrl,
                chatMessage.message,
                chatMessage.createdAt
            )
        )
            .from(chatRoom)
            .innerJoin(member)
            .on(member.id.eq(chatRoom.seller.id).or(member.id.eq(chatRoom.buyer.id)).and(member.id.ne(memberId)))
            .innerJoin(chatMessage).on(chatMessage.chatRoom.id.eq(chatRoom.id))
            .leftJoin(productImage).on(chatRoom.product.id.eq(productImage.product.id))
            .where(chatRoom.buyer.id.eq(memberId).or(chatRoom.seller.id.eq(memberId)))
            .groupBy(chatRoom.id)
            .fetch()
    }
}