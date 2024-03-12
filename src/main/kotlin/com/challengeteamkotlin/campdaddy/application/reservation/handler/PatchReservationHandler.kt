package com.challengeteamkotlin.campdaddy.application.reservation.handler

import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationEntity
import com.challengeteamkotlin.campdaddy.domain.model.reservation.ReservationStatus
import org.springframework.stereotype.Component

@Component
class PatchReservationHandler {


    fun checkReservationPolicy(
        reservation: ReservationEntity,
        requester: Long,
        reservationStatus: ReservationStatus
    ): Boolean {
        val buyer = reservation.member.id
        val seller = reservation.product.member.id

        if (requester !in listOf(buyer, seller)) {
            return false
        }

        when (reservation.reservationStatus) {
            ReservationStatus.REQ -> {
                if (reservationStatus == ReservationStatus.CONFIRM && requester == seller) return true
                if (reservationStatus == ReservationStatus.CANCELED) return true
                return false
            }

            ReservationStatus.CONFIRM -> {
                if (reservationStatus == ReservationStatus.RENT && requester == buyer) return true
                if (reservationStatus == ReservationStatus.CANCELED && requester == seller) return true
                return false
            }

            ReservationStatus.RENT -> {
                if (reservationStatus == ReservationStatus.END && requester == seller) return true
                if (reservationStatus == ReservationStatus.CANCELED && requester == seller) return true
                return false
            }

            ReservationStatus.END, ReservationStatus.CANCELED -> {
                return false
            }
        }
    }

}