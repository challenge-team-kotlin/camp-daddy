package com.challengeteamkotlin.campdaddy.application.reservation

import com.challengeteamkotlin.campdaddy.application.member.exception.MemberErrorCode
import com.challengeteamkotlin.campdaddy.application.product.exception.ProductErrorCode
import com.challengeteamkotlin.campdaddy.application.reservation.exception.AlreadyReservedDateException
import com.challengeteamkotlin.campdaddy.application.reservation.exception.EndDateLessThanStartDateException
import com.challengeteamkotlin.campdaddy.application.reservation.exception.InvalidReservationPatchRequest
import com.challengeteamkotlin.campdaddy.application.reservation.exception.ReservationErrorCode
import com.challengeteamkotlin.campdaddy.application.reservation.handler.PatchReservationHandler
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust.CreateReservationRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.response.ReservationResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class ReservationService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
    private val reservationRepository: ReservationRepository,
    private val patchReservationHandler: PatchReservationHandler
) {

    @Transactional
    fun createReservation(memberId: Long, createReservationRequest: CreateReservationRequest) {
        val memberEntity: MemberEntity = memberRepository.findByIdOrNull(memberId)
            ?: throw EntityNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND)
        val productEntity = productRepository.findByIdOrNull(createReservationRequest.productId)
            ?: throw EntityNotFoundException(ProductErrorCode.PRODUCT_NOT_FOUND_EXCEPTION)


        if (createReservationRequest.endDate lessThan createReservationRequest.startDate) {
            throw EndDateLessThanStartDateException(ReservationErrorCode.END_DATE_LESS_THAN_START_DATE)
        }

        if (checkAlreadyReserved(
                createReservationRequest.productId, createReservationRequest.startDate, createReservationRequest.endDate
            )
        ) throw AlreadyReservedDateException(ReservationErrorCode.ALREADY_RESERVED_DATE)


        val totalPrice = productEntity.pricePerDay *
                getDateDiff(createReservationRequest.startDate, createReservationRequest.endDate)

        createReservationRequest
            .of(productEntity, memberEntity, totalPrice)
            .apply { reservationRepository.createReservation(this) }
    }

    @Transactional
    fun patchReservationStatus(
        reservationId: Long,
        memberId: Long,
        reservationStatus: ReservationStatus
    ) {
        val reservation = reservationRepository.getReservationByIdOrNull(reservationId)
            ?: throw EntityNotFoundException(ReservationErrorCode.RESERVATION_ENTITY_NOT_FOUND)

        if (!patchReservationHandler.checkReservationPolicy(reservation, memberId, reservationStatus)) {
            throw InvalidReservationPatchRequest(ReservationErrorCode.INVALID_RESERVATION_PATCH_REQUEST)
        }

        reservation.reservationStatus = reservationStatus
    }

    @Transactional(readOnly = true)
    fun getProductReservations(productId: Long, pageNo: Int, pageSize: Int): Page<ReservationResponse> =
        reservationRepository.getReservationsProductId(productId, PageRequest.of(pageNo, pageSize))
            .map { ReservationResponse.from(it) }


    @Transactional(readOnly = true)
    fun getMemberReservations(memberId: Long, pageNo: Int, pageSize: Int): Page<ReservationResponse> =
        reservationRepository.getReservationByMemberId(memberId, PageRequest.of(pageNo, pageSize))
            .map { ReservationResponse.from(it) }


    @Transactional(readOnly = true)
    fun getReservation(reservationId: Long): ReservationResponse =
        reservationRepository.getReservationByIdOrNull(reservationId)
            ?.let {
                ReservationResponse.from(it)
            } ?: throw EntityNotFoundException(ReservationErrorCode.RESERVATION_ENTITY_NOT_FOUND)


    private fun getDateDiff(startDate: LocalDate, endDate: LocalDate): Long {
        val dateDiff = ChronoUnit.DAYS.between(startDate, endDate)
        return if (dateDiff == 0L) 1 else dateDiff
    }

    private fun checkAlreadyReserved(productId: Long, startDate: LocalDate, endDate: LocalDate): Boolean {
        return reservationRepository.getFirstReservation(
            productId,
            startDate,
            endDate
        )?.let { true } ?: false
    }

    private infix fun LocalDate.lessThan(anotherDate: LocalDate): Boolean {
        return this < anotherDate
    }

}