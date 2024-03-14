package com.challengeteamkotlin.campdaddy.infrastructure.hibernate.review

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import com.challengeteamkotlin.campdaddy.domain.repository.review.ReviewRepository
import org.springframework.data.repository.findByIdOrNull

class ReviewRepositoryImpl(
    private val reviewJpaRepository: ReviewJpaRepository
) : ReviewRepository {

    override fun createReview(reviewEntity: ReviewEntity): ReviewEntity {
        return reviewJpaRepository.save(reviewEntity)
    }

    override fun getReviewByIdOrNull(reviewId: Long): ReviewEntity? {
        return reviewJpaRepository.findByIdOrNull(reviewId)
    }

    override fun getReviewsByMemberId(memberId: Long): List<ReviewEntity>? {
        return reviewJpaRepository.findByMemberId(memberId)
    }

    override fun getReviewsByProductId(productId: Long): List<ReviewEntity>? {
        return reviewJpaRepository.findByProductId(productId)
    }

    override fun deleteReview(reviewEntity: ReviewEntity) {
        return reviewJpaRepository.delete(reviewEntity)
    }

    override fun isExistsByProductIdAndMemberId(productId: Long, memberId: Long): Boolean {
        return reviewJpaRepository.existsByProductIdAndMemberId(productId, memberId)
    }
}