package com.challengeteamkotlin.campdaddy.domain.model.product

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import jakarta.persistence.*

@Entity
class LikeEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val product: ProductEntity,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        val member: MemberEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;
}