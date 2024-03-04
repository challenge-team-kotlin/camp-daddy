package com.challengeteamkotlin.campdaddy.domain.model.review

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "reviews_image")
class ReviewImageEntity(
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "review_id")
        val reviewEntity: ReviewEntity,

        @Column(name = "image", nullable = false)
        val image: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    val id: Long? = null;
}
