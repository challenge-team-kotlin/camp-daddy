package com.challengeteamkotlin.campdaddy.presentation.reservation.context.event

import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventPublisher
import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventSubscriber
import com.challengeteamkotlin.campdaddy.infrastructure.aws.SnsEventPublisher
import com.challengeteamkotlin.campdaddy.infrastructure.aws.SqsEventSubscriber
import com.challengeteamkotlin.campdaddy.infrastructure.event.reservation.ReservationEventPublisherImpl
import com.challengeteamkotlin.campdaddy.infrastructure.event.reservation.ReservationEventSubscriberImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReservationEventConfig {

    @Bean
    fun reservationEventPublisher(snsEventPublisher: SnsEventPublisher): ReservationEventPublisher {
        return ReservationEventPublisherImpl(snsEventPublisher)
    }

    @Bean
    fun reservationEventSubscriber(sqsEventSubscriber: SqsEventSubscriber): ReservationEventSubscriber {
        return ReservationEventSubscriberImpl(sqsEventSubscriber)
    }

}