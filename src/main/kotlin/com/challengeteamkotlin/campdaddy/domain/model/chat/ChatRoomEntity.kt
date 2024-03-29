package com.challengeteamkotlin.campdaddy.domain.model.chat

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_rooms")
class ChatRoomEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", updatable = false)
    val buyer: MemberEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", updatable = false)
    val seller: MemberEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", updatable = false)
    val product: ProductEntity,

    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    var id: Long? = null

}
