package com.challengeteamkotlin.campdaddy.domain.model.review

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.product.ProductEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "reviews")
@SQLDelete(sql = "UPDATE reviews SET is_deleted = true WHERE review_id = ?")
class ReviewEntity(
        @Column(name = "content")
        var content: String,

        @Column(name = "score")
        var score: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        val member: MemberEntity,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        val product: ProductEntity
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    val id: Long? = null

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "review", orphanRemoval = true)
    protected val mutableImages: MutableList<ReviewImageEntity> = mutableListOf()

    val images: List<ReviewImageEntity>
        get() = mutableImages.toList()

    fun uploadImage(reviewImageEntity: ReviewImageEntity) {
        mutableImages.add(reviewImageEntity)
    }

    fun deleteImage(reviewImageEntity: ReviewImageEntity) {
        mutableImages.remove(reviewImageEntity)
    }

}