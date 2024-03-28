package com.challengeteamkotlin.campdaddy.infrastructure.event.reservation

import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventPublisher
import com.challengeteamkotlin.campdaddy.infrastructure.aws.SnsEventPublisher
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event.ReservationEvent
import org.springframework.beans.factory.annotation.Value

class ReservationEventPublisherImpl(
    private val snsEventPublisher: SnsEventPublisher,
) : ReservationEventPublisher {
    @Value("\${aws.sns-arn.reservation-change}")
    lateinit var snsArn: String

    override fun publish(reservationEvent: ReservationEvent) {
        val groupId = "${reservationEvent.buyerId},${reservationEvent.sellerId},${reservationEvent.productId}"
        snsEventPublisher.publishEvent(groupId, snsArn, reservationEvent)
    }
}