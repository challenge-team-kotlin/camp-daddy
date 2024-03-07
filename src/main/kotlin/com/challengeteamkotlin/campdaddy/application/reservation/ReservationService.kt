package com.challengeteamkotlin.campdaddy.application.reservation

import com.challengeteamkotlin.campdaddy.application.reservation.exception.AlreadyReservedDateException
import com.challengeteamkotlin.campdaddy.application.reservation.exception.ReservationErrorCode
import com.challengeteamkotlin.campdaddy.application.reservation.exception.StartDateLessThanEndDateException
import com.challengeteamkotlin.campdaddy.common.exception.EntityNotFoundException
import com.challengeteamkotlin.campdaddy.domain.model.member.MemberEntity
import com.challengeteamkotlin.campdaddy.domain.repository.member.MemberRepository
import com.challengeteamkotlin.campdaddy.domain.repository.product.ProductRepository
import com.challengeteamkotlin.campdaddy.domain.repository.reservation.ReservationRepository
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust.CreateReservationRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.reqeust.PatchReservationStatusRequest
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.response.ReservationResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class ReservationService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
    private val reservationRepository: ReservationRepository
) {

    @Transactional
    fun createReservation(createReservationRequest: CreateReservationRequest) {
        val memberEntity: MemberEntity = memberRepository.findByIdOrNull(createReservationRequest.memberId)
            ?: TODO("throw EntityNotFoundException()")
        val productEntity = productRepository.findByIdOrNull(createReservationRequest.productId)
            ?: TODO("throw EntityNotFoundException()")

        if (createReservationRequest.startDate lessThan createReservationRequest.endDate) {
            throw StartDateLessThanEndDateException(ReservationErrorCode.START_DATE_LESS_THAN_END_DATE)
        }

        if (checkAlreadyReserved(
                createReservationRequest.productId, createReservationRequest.startDate, createReservationRequest.endDate
            )
        ) throw AlreadyReservedDateException(ReservationErrorCode.ALREADY_RESERVED_DATE)


        val totalPrice = productEntity.pricePerDay *
                getDateDiff(createReservationRequest.startDate, createReservationRequest.endDate)

        createReservationRequest
            .toEntity(productEntity, memberEntity, totalPrice)
            .apply { reservationRepository.save(this) }
    }

    @Transactional
    fun patchReservationStatus(patchReservationStatusRequest: PatchReservationStatusRequest) {
        val reservation = reservationRepository.findByIdOrNull(patchReservationStatusRequest.reservationId!!)
            ?: throw EntityNotFoundException(ReservationErrorCode.RESERVATION_ENTITY_NOT_FOUND)

        if (reservation.member.id != patchReservationStatusRequest.memberId
            && reservation.product.member.id != patchReservationStatusRequest.memberId
        ) {
            throw TODO("throw 권한없음")
        }

        reservation.reservationStatus = patchReservationStatusRequest.reservationStatus
    }

    @Transactional(readOnly = true)
    fun getProductReservations(productId: Long): List<ReservationResponse> =
        reservationRepository.findByProductId(productId)?.map { ReservationResponse.from(it) }
            ?: emptyList()


    @Transactional(readOnly = true)
    fun getMemberReservations(memberId: Long): List<ReservationResponse> =
        reservationRepository.findByMemberId(memberId)?.map { ReservationResponse.from(it) }
            ?: emptyList()


    @Transactional(readOnly = true)
    fun getReservation(reservationId: Long): ReservationResponse =
        reservationRepository.findByIdOrNull(reservationId)
            ?.let {
                ReservationResponse.from(it)
            } ?: throw EntityNotFoundException(ReservationErrorCode.RESERVATION_ENTITY_NOT_FOUND)


    private fun getDateDiff(startDate: LocalDate, endDate: LocalDate): Long {
        val dateDiff = ChronoUnit.DAYS.between(startDate, endDate)
        return if(dateDiff == 0L) 1 else dateDiff
    }

    private fun checkAlreadyReserved(productId: Long, startDate: LocalDate, endDate: LocalDate): Boolean {
        return reservationRepository.findFirstByProductIdAndStartDateAndEndDate(
            productId,
            startDate,
            endDate
        )?.let { true } ?: false
    }

    private infix fun LocalDate.lessThan(anotherDate: LocalDate): Boolean {
        return this > anotherDate
    }

}