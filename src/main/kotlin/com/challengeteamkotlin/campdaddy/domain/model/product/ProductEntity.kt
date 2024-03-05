package com.challengeteamkotlin.campdaddy.domain.model.product

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE members SET is_deleted = true WHERE id = ?")
class ProductEntity(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    val memberEntity: MemberEntity,

    @Column(name = "price_per_day", nullable = false)
    val pricePerDay: Long,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    val category: Category

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null

    @OneToMany(mappedBy = "productEntity", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = false)
    protected val mutableReviews: MutableList<ReviewEntity> = mutableListOf()
    val reviews: List<ReviewEntity>
        get() = mutableReviews.toList()

    fun addReview(reviewEntity: ReviewEntity) {
        mutableReviews.add(reviewEntity)
    }

    @OneToMany(mappedBy = "productEntity", cascade = [CascadeType.ALL], orphanRemoval = false)
    protected val mutableImages: MutableList<ProductImageEntity> = mutableListOf()
    val images: List<ProductImageEntity>
        get() = mutableImages.toList()

    fun uploadImage(productImageEntity: ProductImageEntity) {
        mutableImages.add(productImageEntity)
    }

}
