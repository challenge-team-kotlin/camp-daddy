package com.challengeteamkotlin.campdaddy.application.reservation

import com.challengeteamkotlin.campdaddy.application.reservation.exception.EndDateLessThanStartDateException
import com.challengeteamkotlin.campdaddy.application.reservation.exception.InvalidReservationPatchRequest
import com.challengeteamkotlin.campdaddy.application.reservation.handler.PatchReservationHandler
import com.challengeteamkotlin.campdaddy.common.exception.CustomException
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import com.challengeteamkotlin.campdaddy.fixture.member.MemberFixture
import com.challengeteamkotlin.campdaddy.fixture.product.ProductFixture
import com.challengeteamkotlin.campdaddy.fixture.reservation.DateFixture
import com.challengeteamkotlin.campdaddy.fixture.reservation.ReservationFixture
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust.CreateReservationRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.response.ReservationResponse
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull

@ExtendWith(MockKExtension::class)
class ReservationServiceTest : DescribeSpec({
    extension(SpringExtension)

    afterContainer {
        clearAllMocks()
    }

    val productRepository = mockk<ProductRepository>()
    val memberRepository = mockk<MemberRepository>()
    val reservationRepository = mockk<ReservationRepository>()
    val patchReservationHandler = PatchReservationHandler()

    val reservationService =
        ReservationService(productRepository, memberRepository, reservationRepository, patchReservationHandler)

    describe("예약 생성 테스트") {
        context("주어진 memberId로 Entity를 찾을 수 없을 경우") {
            val createReservationRequest = CreateReservationRequest(1L, DateFixture.today, DateFixture.tomorrow)
            every { memberRepository.findByIdOrNull(any()) } returns null
            every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
            it("EntityNotFoundException이 발생한다.") {
                shouldThrow<EntityNotFoundException> {
                    reservationService.createReservation(1L, createReservationRequest)
                }
            }
        }
        context("주어진 productId로 Entity를 찾을 수 없을 경우") {
            val createReservationRequest = CreateReservationRequest(1L, DateFixture.today, DateFixture.tomorrow)
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns null
            it("EntityNotFoundException이 발생한다.") {
                shouldThrow<EntityNotFoundException> {
                    reservationService.createReservation(1L, createReservationRequest)
                }
            }
        }
        context("endDate가 startDate보다 작을 경우") {
            val createReservationRequest = CreateReservationRequest(1L, DateFixture.tomorrow, DateFixture.today)
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
            it("EndDateLessThanStartDateException이 발생한다.") {
                shouldThrow<EndDateLessThanStartDateException> {
                    reservationService.createReservation(1L, createReservationRequest)
                }
            }
        }
        context("이미 예약이 이루어져 있을 경우") {
            val createReservationRequest = CreateReservationRequest(1L, DateFixture.tomorrow, DateFixture.today)
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
            every {
                reservationRepository.getFirstReservation(any(), any(), any())
            } returns ReservationFixture.reservation
            it("EndDateLessThanStartDateException이 발생한다.") {
                shouldThrow<EndDateLessThanStartDateException> {
                    reservationService.createReservation(1L, createReservationRequest)
                }
            }
        }
        context("위 사항이 모두 지켜졌을 경우") {
            val createReservationRequest = CreateReservationRequest(1L, DateFixture.today, DateFixture.tomorrow)
            every { memberRepository.findByIdOrNull(any()) } returns MemberFixture.buyer.apply { this.id = 1L }
            every { productRepository.findByIdOrNull(any()) } returns ProductFixture.tent.apply { this.id = 1L }
            every {
                reservationRepository.getFirstReservation(any(), any(), any())
            } returns null
            every { reservationRepository.createReservation(any()) } returns ReservationFixture.reservation
            it("Exception이 발생하지 않는다.") {
                shouldNotThrow<CustomException> {
                    reservationService.createReservation(1L, createReservationRequest)
                }
            }
        }
    }

    describe("예약 수정 테스트") {
        context("주어진 memberId가 buyer, seller 모두 아닐 경우") {
            every { reservationRepository.getReservationByIdOrNull(1L) } returns
                    ReservationFixture.reservation.apply {
                        member.id = 1L
                        product.member.id = 2L
                        reservationStatus = ReservationStatus.REQ
                    }
            it("InvalidReservationPatchRequest가 발생한다") {
                shouldThrow<InvalidReservationPatchRequest> {
                    reservationService.patchReservationStatus(1L, 3L, ReservationStatus.REQ)
                }
            }
        }
        context("Req에서 Confirm으로 변경을 buyer가 요청한 경우") {
            every { reservationRepository.getReservationByIdOrNull(1L) } returns
                    ReservationFixture.reservation.apply {
                        member.id = 1L
                        product.member.id = 2L
                        reservationStatus = ReservationStatus.REQ
                    }
            it("InvalidReservationPatchRequest가 발생한다") {
                shouldThrow<InvalidReservationPatchRequest> {
                    reservationService.patchReservationStatus(1L, 1L, ReservationStatus.CONFIRM)
                }
            }
        }
        context("Req에서 Confirm으로 변경을 seller가 요청한 경우") {
            every { reservationRepository.getReservationByIdOrNull(1L) } returns
                    ReservationFixture.reservation.apply {
                        member.id = 1L
                        product.member.id = 2L
                        reservationStatus = ReservationStatus.REQ
                    }
            reservationService.patchReservationStatus(1L, 2L, ReservationStatus.CONFIRM)
            it("변경이 일어난다") {
                ReservationFixture.reservation.reservationStatus = ReservationStatus.CONFIRM
            }
        }
    }

    describe("예약 단건 조회") {
        context("주어진 reservationId의 데이터가 없을 경우") {
            every { reservationRepository.getReservationByIdOrNull(any()) } returns null
            it("EntityNotFoundException이 발생한다.") {
                shouldThrow<EntityNotFoundException> {
                    reservationService.getReservation(1L)
                }
            }
        }
        context("주어진 reservationId의 데이터가 있을 경우") {
            every { reservationRepository.getReservationByIdOrNull(1L) } returns ReservationFixture.reservation.apply {
                this.id = 1L
                this.member.id = 1L
                this.product.id = 1L
            }
            it("ReservationResponse를 반환한다.") {
                reservationService.getReservation(1L).shouldBeTypeOf<ReservationResponse>()
            }
        }
    }
}
)