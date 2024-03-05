package com.challengeteamkotlin.campdaddy.domain.model.product

import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class LikeEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val productEntity: ProductEntity,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        val memberEntity: MemberEntity
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null;
}