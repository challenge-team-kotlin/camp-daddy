package com.challengeteamkotlin.campdaddy.domain.model.chat

import com.challengeteamkotlin.campdaddy.common.entity.BaseTimeEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_messages")
class ChatMessageEntity(

    @Column
    val message: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: MemberEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoomEntity,

    @Column
    @Enumerated(EnumType.STRING)
    val status: MessageStatus

    ) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}
