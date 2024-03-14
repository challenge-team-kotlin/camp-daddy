package com.challengeteamkotlin.campdaddy.application.review

import com.challengeteamkotlin.campdaddy.application.review.execption.ChangeReviewRefusedException
import com.challengeteamkotlin.campdaddy.application.review.execption.CreateReviewRefusedException
import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import com.challengeteamkotlin.campdaddy.domain.repository.review.ReviewRepository
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture
import com.challengeteamkotlin.campdaddy.fixture.review.ReviewFixture
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.CreateReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.DeleteReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.request.PatchReviewRequest
import com.challengeteamkotlin.campdaddy.presentation.review.dto.response.ReviewResponse
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull

@ExtendWith(MockKExtension::class)
class ReviewServiceTest : DescribeSpec({
    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    val productRepository = mockk<ProductRepository>()
    val memberRepository = mockk<MemberRepository>()
    val reservationRepository = mockk<ReservationRepository>()
    val reviewRepository = mockk<ReviewRepository>()

    val reviewService = ReviewService(productRepository, memberRepository, reviewRepository, reservationRepository)

    describe("리뷰 생성 테스트") {
        context("주어진 memberId로 Entity를 찾을 수 없을 경우") {
            val createReservationRequest =
                CreateReviewRequest("리뷰 테스트", 1L, 5, listOf("https://image1.com", "https://image2.com"))
            every { memberRepository.findByIdOrNull(any()) } returns null
            it("EntityNotFoundException이 발생한다.") {
                shouldThrow<EntityNotFoundException> {
                    reviewService.createReview(1L, createReservationRequest)
                }
            }
        }
        context("주어진 productId로 Entity를 찾을 수 없을 경우") {
            val createReservationRequest =
                CreateReviewRequest("리뷰 테스트", 1L, 5, listOf("https://image1.com", "https://image2.com"))
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns null
            it("EntityNotFoundException이 발생한다.") {
                shouldThrow<EntityNotFoundException> {
                    reviewService.createReview(1L, createReservationRequest)
                }
            }
        }
        context("주어진 productId를 구매한 기록이 없을 경우") {
            val createReservationRequest =
                CreateReviewRequest("리뷰 테스트", 1L, 5, listOf("https://image1.com", "https://image2.com"))
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
            every {
                reservationRepository.isExistsReservation(
                    any(), any(), any()
                )
            } returns false
            every { reviewRepository.existsByProductIdAndMemberId(any(), any()) } returns false
            it("CreateReviewRefusedException 발생한다.") {
                shouldThrow<CreateReviewRefusedException> {
                    reviewService.createReview(1L, createReservationRequest)
                }
            }
        }

        context("주어진 productId에 이미 리뷰를 남겼을 경우") {
            val createReservationRequest =
                CreateReviewRequest("리뷰 테스트", 1L, 5, listOf("https://image1.com", "https://image2.com"))
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
            every {
                reservationRepository.isExistsReservation(
                    any(), any(), any()
                )
            } returns true
            every { reviewRepository.existsByProductIdAndMemberId(any(), any()) } returns true
            it("CreateReviewRefusedException 발생한다.") {
                shouldThrow<CreateReviewRefusedException> {
                    reviewService.createReview(1L, createReservationRequest)
                }
            }
        }

        context("위 사항이 모두 지켜졌을 경우") {
            val createReservationRequest =
                CreateReviewRequest("리뷰 테스트", 1L, 5, listOf("https://image1.com", "https://image2.com"))
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
            every {
                reservationRepository.isExistsReservation(
                    any(), any(), any()
                )
            } returns true
            every { reviewRepository.existsByProductIdAndMemberId(any(), any()) } returns false
            every { reviewRepository.save(any()) } returns ReviewFixture.review
            it("Exception이 발생하지 않는다.") {
                shouldNotThrow<CustomException> {
                    reviewService.createReview(1L, createReservationRequest)
                }
            }
        }

        describe("리뷰 수정 테스트") {
            context("주어진 memberId로 Entity를 찾을 수 없을 경우") {
                val patchReviewRequest =
                    PatchReviewRequest("change review", 5, listOf("https://image1.com", "https://image2.com"))

                every { memberRepository.findByIdOrNull(any()) } returns null
                it("EntityNotFoundException이 발생한다.") {
                    shouldThrow<EntityNotFoundException> {
                        reviewService.patchReview(1L, 1L, patchReviewRequest)
                    }
                }
            }
            context("주어진 reviewId로 Entity를 찾을 수 없을 경우") {
                val patchReviewRequest =
                    PatchReviewRequest("change review", 5, listOf("https://image1.com", "https://image2.com"))
                every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
                every { reviewRepository.findByIdOrNull(any()) } returns null
                it("EntityNotFoundException이 발생한다.") {
                    shouldThrow<EntityNotFoundException> {
                        reviewService.patchReview(1L, 1L, patchReviewRequest)
                    }
                }
            }
            context("리뷰를 생성한 멤버가 아닌 멤버가 수정을 시도할 경우") {
                val patchReviewRequest =
                    PatchReviewRequest("change review", 5, listOf("https://image1.com", "https://image2.com"))
                every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.seller.apply { this.id = 1L }
                every { reviewRepository.findByIdOrNull(any()) } returns ReviewFixture.review.apply {
                    this.member.id = 2L
                    this.product.id = 1L
                }
                it("ChangeReviewRefusedException 발생한다.") {
                    shouldThrow<ChangeReviewRefusedException> {
                        reviewService.patchReview(1L, 1L, patchReviewRequest)
                    }
                }
            }

            context("위 사항이 모두 지켜졌을 경우") {
                val patchReviewRequest =
                    PatchReviewRequest("change review", 5, listOf("https://image1.com", "https://image2.com"))
                every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
                every { reviewRepository.findByIdOrNull(any()) } returns ReviewFixture.review.apply {
                    this.member.id = 1L
                    this.product.id = 1L
                }
                it("Exception이 발생하지 않는다.") {
                    shouldNotThrow<CustomException> {
                        reviewService.patchReview(1L, 1L, patchReviewRequest)
                    }
                }
            }
        }

        describe("리뷰 삭제 테스트") {
            context("주어진 memberId로 Entity를 찾을 수 없을 경우") {
                val deleteReviewRequest = DeleteReviewRequest(1L, 1L)
                every { memberRepository.findByIdOrNull(any()) } returns null
                every { reviewRepository.findByIdOrNull(any()) } returns ReviewFixture.review.apply {
                    this.member.id = 1L
                    this.product.id = 1L
                }
                it("EntityNotFoundException이 발생한다.") {
                    shouldThrow<EntityNotFoundException> {
                        reviewService.deleteReview(deleteReviewRequest)
                    }
                }
            }
            context("주어진 reviewId로 Entity를 찾을 수 없을 경우") {
                val deleteReviewRequest = DeleteReviewRequest(1L, 1L)
                every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
                every { reviewRepository.findByIdOrNull(any()) } returns null
                it("EntityNotFoundException이 발생한다.") {
                    shouldThrow<EntityNotFoundException> {
                        reviewService.deleteReview(deleteReviewRequest)
                    }
                }
            }

            context("리뷰를 생성한 멤버가 아닌 멤버가 삭제를 시도할 경우") {
                val deleteReviewRequest = DeleteReviewRequest(1L, 1L)
                every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.seller.apply { this.id = 1L }
                every { reviewRepository.findByIdOrNull(any()) } returns ReviewFixture.review.apply {
                    this.member.id = 2L
                    this.product.id = 1L
                }
                it("ChangeReviewRefusedException 발생한다.") {
                    shouldThrow<ChangeReviewRefusedException> {
                        reviewService.deleteReview(deleteReviewRequest)
                    }
                }
            }

            context("위 사항이 모두 지켜졌을 경우") {
                val deleteReviewRequest = DeleteReviewRequest(1L, 1L)
                every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
                every { reviewRepository.findByIdOrNull(any()) } returns ReviewFixture.review.apply {
                    this.member.id = 1L
                    this.product.id = 1L
                }
                every { reviewRepository.delete(any()) } returns Unit
                it("Exception이 발생하지 않는다.") {
                    shouldNotThrow<CustomException> {
                        reviewService.deleteReview(deleteReviewRequest)
                    }
                }
            }
        }

        describe("리뷰 조회 테스트") {
            context("멤버 아이디가 주어졌을 때") {
                every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
                every { reviewRepository.findByMemberId(any()) } returns listOf(ReviewFixture.review.apply {
                    this.id = 1L
                })

                it("ReviewResponse가 반환 된다.") {
                    reviewService.getMemberReviews(1L)[0].shouldBeInstanceOf<ReviewResponse>()
                }
            }
            context("상품 아이디가 주어졌을 때") {
                every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
                every { reviewRepository.findByProductId(any()) } returns listOf(ReviewFixture.review.apply {
                    this.id = 1L
                })

                it("ReviewResponse가 반환 된다.") {
                    reviewService.getProductReviews(1L)[0].shouldBeInstanceOf<ReviewResponse>()
                }
            }

        }
    }
})