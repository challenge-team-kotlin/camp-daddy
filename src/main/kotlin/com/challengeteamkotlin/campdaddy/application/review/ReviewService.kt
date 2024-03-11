package com.challengeteamkotlin.campdaddy.application.review

import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.application.product.exception.ProductErrorCode
import com.challengeteamkotlin.campdaddy.application.review.execption.ChangeReviewRefusedException
import com.challengeteamkotlin.campdaddy.application.review.execption.CreateReviewRefusedException
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
    fun createReview(memberId: Long, createReviewRequest: CreateReviewRequest) {
        val memberEntity: MemberEntity = memberRepository.findByIdOrNull(memberId)
            ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)

        val productEntity = productRepository.findByIdOrNull(createReviewRequest.productId)
            ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

        if (!checkBoughtBefore(productEntity.id!!, memberEntity.id!!)) {
            throw CreateReviewRefusedException(ReviewErrorCode.DO_NOT_BOUGHT_BEFORE)
        }

        if (!checkAlreadyCreateReview(productEntity.id!!, memberEntity.id!!)) {
            throw CreateReviewRefusedException(ReviewErrorCode.ALREADY_CREATE_REVIEW)
        }

        createReviewRequest
            .of(productEntity, memberEntity)
            .apply {
                createReviewRequest.imageUrls!!
                    .map { ReviewImageEntity(this, it) }
                    .forEach { this.uploadImage(it) }
            }.run {
                reviewRepository.save(this)
            }
    }

    @Transactional
    fun patchReview(memberId: Long, reviewId: Long, patchReviewRequest: PatchReviewRequest) {
        val member: MemberEntity = memberRepository.findByIdOrNull(memberId)
            ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
        val review: ReviewEntity = reviewRepository.findByIdOrNull(reviewId)
            ?: throw EntityNotFoundException(ReviewErrorCode.REVIEW_ENTITY_NOT_FOUND)

        if (member.id != memberId) {
            throw ChangeReviewRefusedException(ReviewErrorCode.DO_NOT_HAVE_AUTHORITY)
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
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)

        return reviewRepository
            .findByMemberId(member.id!!)
            ?.map { ReviewResponse.from(it) }
            ?: emptyList()
    }

    @Transactional(readOnly = true)
    fun getProductReviews(getProductsReviewRequest: GetProductsReviewRequest): List<ReviewResponse> {
        val product = productRepository.findByIdOrNull(getProductsReviewRequest.productId)
            ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)

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
            throw ChangeReviewRefusedException(ReviewErrorCode.DO_NOT_HAVE_AUTHORITY)
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
