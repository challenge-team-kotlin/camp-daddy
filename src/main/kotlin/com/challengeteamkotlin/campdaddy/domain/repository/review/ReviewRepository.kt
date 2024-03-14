package com.challengeteamkotlin.campdaddy.domain.repository.review

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity

interface ReviewRepository {

    fun createReview(reviewEntity: ReviewEntity): ReviewEntity
    fun getReviewByIdOrNull(reviewId: Long): ReviewEntity?
    fun getReviewsByMemberId(memberId: Long): List<ReviewEntity>?
    fun getReviewsByProductId(productId: Long): List<ReviewEntity>?
    fun deleteReview(reviewEntity: ReviewEntity)
    fun isExistsByProductIdAndMemberId(productId: Long, memberId: Long): Boolean

}