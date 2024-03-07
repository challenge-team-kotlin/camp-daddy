package com.challengeteamkotlin.campdaddy.presentation.reservation.dto.response

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import java.time.LocalDate

data class ReservationResponse(
    val reservationId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val productTitle: String,
    val totalPrice: Long,
    val reservationStatus: ReservationStatus,
    val memberId: Long,
    val memberName: String,

) {
    companion object {
        fun from(reservationEntity: ReservationEntity) = ReservationResponse(
            reservationId = reservationEntity.id!!,
            startDate = reservationEntity.startDate,
            endDate = reservationEntity.endDate,
            productTitle = reservationEntity.product.title,
            totalPrice = reservationEntity.totalPrice,
            reservationStatus = reservationEntity.reservationStatus,
            memberId = reservationEntity.member.id!!,
            memberName = reservationEntity.member.name
        )
    }
}