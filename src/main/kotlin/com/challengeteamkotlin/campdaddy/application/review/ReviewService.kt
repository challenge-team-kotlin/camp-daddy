package com.challengeteamkotlin.campdaddy.application.review

import com.challengeteamkotlin.campdaddy.application.review.execption.ReviewErrorCode
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewEntity
import com.challengeteamkotlin.campdaddy.domain.model.review.ReviewImageEntity
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import com.challengeteamkotlin.campdaddy.domain.repository.review.ReviewRepository
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.CreateReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.DeleteReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.GetProductsReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.PatchReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.response.ReviewResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
    private val reviewRepository: ReviewRepository,
    private val reservationRepository: ReservationRepository
) {


    @Transactional
    fun createReview(createReviewRequest: CreateReviewRequest) {
        val memberEntity: MemberEntity = memberRepository.findByIdOrNull(createReviewRequest.memberId)
            ?: TODO("throw EntityNotFoundException()")

        val productEntity = productRepository.findByIdOrNull(createReviewRequest.productId)
            ?: TODO("throw EntityNotFoundException()")

        if (!checkBoughtBefore(productEntity.id!!, memberEntity.id!!)) {
            throw TODO("Unauthorized")
        }

        if (!checkAlreadyCreateReview(productEntity.id!!, memberEntity.id!!)) {
            throw TODO("Unauthorized")
        }

        createReviewRequest
            .of(productEntity, memberEntity)
            .run {
                createReviewRequest.imageUrls!!
                    .map { ReviewImageEntity(this, it) }
                    .forEach { this.uploadImage(it) }
                reviewRepository.save(this)
            }
    }

    @Transactional
    fun patchReview(patchReviewRequest: PatchReviewRequest) {
        val member: MemberEntity = memberRepository.findByIdOrNull(patchReviewRequest.memberId)
            ?: TODO("throw EntityNotFoundException()")
        val review: ReviewEntity = reviewRepository.findByIdOrNull(patchReviewRequest.reviewId)
            ?: TODO("throw EntityNotFoundException()")

        if (member.id != patchReviewRequest.memberId) {
            throw TODO("Unauthorized exception")
        }


        review.content = patchReviewRequest.content
        review.score = patchReviewRequest.score

        review.images
            .filterNot { patchReviewRequest.reviewImageUrls.contains(it.imageUrl) }
            .forEach { review.deleteImage(it) }

        patchReviewRequest.reviewImageUrls
            .filterNot { review.images.map { image -> image.imageUrl }.contains(it) }
            .forEach { review.uploadImage(ReviewImageEntity(review, it)) }

    }

    @Transactional(readOnly = true)
    fun getMemberReviews(memberId: Long): List<ReviewResponse> {
        val member = memberRepository.findByIdOrNull(memberId) ?: throw TODO("throw ProductEntityNotFound")

        return reviewRepository
            .findByMemberId(member.id!!)
            ?.map { ReviewResponse.from(it) }
            ?: emptyList()
    }

    @Transactional(readOnly = true)
    fun getProductReviews(getProductsReviewRequest: GetProductsReviewRequest): List<ReviewResponse> {
        val product = productRepository.findByIdOrNull(getProductsReviewRequest.productId)
            ?: throw TODO("throw ProductEntityNotFound")

        return reviewRepository
            .findByProductId(product.id!!)
            ?.map { ReviewResponse.from(it) }
            ?: emptyList()
    }

    @Transactional
    fun deleteReview(deleteReviewRequest: DeleteReviewRequest) {
        val review = reviewRepository.findByIdOrNull(deleteReviewRequest.reviewId)
            ?: throw EntityNotFoundException(ReviewErrorCode.REVIEW_ENTITY_NOT_FOUND)
        if (review.member.id != deleteReviewRequest.memberId) {
            throw TODO("AccessDenied()")
        }

        reviewRepository.delete(review)
    }

    private fun checkBoughtBefore(productId: Long, memberId: Long): Boolean {
        return reservationRepository
            .existsByProductIdAndMemberIdAndReservationStatus(
                productId, memberId, ReservationStatus.END
            )
    }

    private fun checkAlreadyCreateReview(productId: Long, memberId: Long): Boolean {
        return reviewRepository.existsByProductIdAndMemberId(productId, memberId)
    }


}
