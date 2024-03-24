package com.challengeteamkotlin.campdaddy.domain.event.reservation

import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event.ReservationEvent

interface ReservationEventPublisher {
    fun publish(reservationEvent: ReservationEvent)
}