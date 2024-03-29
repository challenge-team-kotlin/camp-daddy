package com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus


data class ReservationEvent(
    val productId: Long,
    val buyerId: Long,
    val sellerId: Long,
    val changeStatus: ReservationStatus
) {

    companion object {
        fun of(reservationEntity: ReservationEntity): ReservationEvent {
            return ReservationEvent(
                productId = reservationEntity.product.id!!,
                buyerId = reservationEntity.member.id!!,
                sellerId = reservationEntity.product.member.id!!,
                changeStatus = reservationEntity.reservationStatus
            )
        }
    }
}