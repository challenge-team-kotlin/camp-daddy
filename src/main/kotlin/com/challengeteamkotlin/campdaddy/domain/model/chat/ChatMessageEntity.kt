package com.challengeteamkotlin.campdaddy.domain.model.chat

import com.challengeteamkotlin.campdaddy.common.entity.BaseTimeEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_messages")
class ChatMessageEntity(

    @Column(length = 200, nullable = false)
    val message: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val memberEntity: MemberEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoomEntity: ChatRoomEntity,

    ) : BaseTimeEntity() {

    init {
        require(message.isNotEmpty())
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}
