package com.challengeteamkotlin.campdaddy.domain.model.review

import jakarta.persistence.*


@Entity
@Table(name = "reviews_image")
class ReviewImageEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "review_id")
        val review: ReviewEntity,

        @Column(name = "image", nullable = false)
        val image: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    val id: Long? = null;
}
