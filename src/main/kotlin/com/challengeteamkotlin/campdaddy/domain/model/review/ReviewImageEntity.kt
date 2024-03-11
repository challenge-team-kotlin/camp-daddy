package com.challengeteamkotlin.campdaddy.domain.model.review

import com.challengeteamkotlin.campdaddy.common.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete


@Entity
@Table(name = "reviews_image")
@SQLDelete(sql = "UPDATE reviews_image SET is_deleted = true WHERE review_image_id = ?")
class ReviewImageEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    val review: ReviewEntity,

    @Column(name = "image_url", nullable = false)
    val imageUrl: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    val id: Long? = null;
}
