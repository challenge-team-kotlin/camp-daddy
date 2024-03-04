package com.challengeteamkotlin.campdaddy.domain.model.review

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "reviews")
@SQLDelete(sql = "UPDATE members SET is_deleted = true WHERE id = ?")
class ReviewEntity(
        @Column(name = "content")
        var content: String,

        @Column(name = "score")
        var score: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        val memberEntity: MemberEntity,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val productEntity: ProductEntity
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    val id: Long? = null

    @OneToMany
    val images: List<ReviewImageEntity> = listOf()
}