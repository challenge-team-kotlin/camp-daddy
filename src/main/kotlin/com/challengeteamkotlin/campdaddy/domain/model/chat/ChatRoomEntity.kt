package com.challengeteamkotlin.campdaddy.domain.model.chat

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_rooms")
class ChatRoomEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    val memberEntity: MemberEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    val productEntity: ProductEntity,

    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val id: Long? = null

}
