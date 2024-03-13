package com.challengeteamkotlin.campdaddy.presentation.review

import com.challengeteamkotlin.campdaddy.application.review.ReviewService
import com.challengeteamkotlin.campdaddy.common.security.UserPrincipal
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.CreateReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.DeleteReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.PatchReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.response.ReviewResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {

    @PostMapping
    fun createReview(
        @RequestBody @Valid createReviewRequest: CreateReviewRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        return reviewService.createReview(memberId = userPrincipal.id, createReviewRequest).let {
            ResponseEntity
                .status(HttpStatus.CREATED)
                .build()
        }
    }

    @PatchMapping("/{reviewId}")
    fun patchReview(
        @PathVariable reviewId: Long,
        @RequestBody @Valid patchReviewRequest: PatchReviewRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {

        return reviewService.patchReview(
            memberId = userPrincipal.id, reviewId = reviewId, patchReviewRequest
        ).run {
            ResponseEntity
                .status(HttpStatus.OK)
                .build()
        }
    }

    @GetMapping("/products/{productId}")
    fun getProductReviews(
        @PathVariable productId: Long
    ): ResponseEntity<List<ReviewResponse>> {
        return reviewService.getProductReviews(productId)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(it)
            }
    }

    @GetMapping("/me")
    fun getMemberReviews(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<List<ReviewResponse>> {

        return reviewService.getMemberReviews(userPrincipal.id)
            .let {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .body(it)
            }
    }

    @DeleteMapping("/{reviewId}")
    fun deleteReview(
        @PathVariable reviewId: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Unit> {
        return reviewService
            .deleteReview(DeleteReviewRequest(reviewId = reviewId, memberId = userPrincipal.id))
            .run {
                ResponseEntity
                    .status(HttpStatus.OK)
                    .build()
            }
    }

}