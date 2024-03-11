package com.challengeteamkotlin.campdaddy.presentation.review.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity

data class ReviewResponse(
    val reviewId: Long,
    val productId: Long,
    val nickName: String,
    val content: String,
    val imageUrls: List<String>
) {
    companion object {
        fun from(review: ReviewEntity): ReviewResponse {
            return ReviewResponse(
                reviewId = review.id!!,
                productId = review.product.id!!,
                nickName = review.member.nickname,
                content = review.content,
                imageUrls = review.images.map { it.imageUrl }
            )
        }
    }
}