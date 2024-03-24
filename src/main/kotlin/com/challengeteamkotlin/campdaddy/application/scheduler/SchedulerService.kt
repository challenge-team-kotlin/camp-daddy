package com.challengeteamkotlin.campdaddy.application.scheduler

import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventSubscriber
import com.challengeteamkotlin.campdaddy.presentation.reservation.dto.event.ReservationEvent
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerService(
    private val reservationEventSubscriber: ReservationEventSubscriber
) {

    @Scheduled(fixedRateString = "5000")
    fun reservationEventListener() {
        val receiveMessages = reservationEventSubscriber.receiveMessages()
        receiveMessages.forEach {
            val reservationEvent = jacksonObjectMapper().readTree(it.body())["Message"]
                .let { jsonNode -> jacksonObjectMapper().readTree(jsonNode.asText()) }
                .let { jsonNode -> jacksonObjectMapper().treeToValue<ReservationEvent>(jsonNode) }

            // TODO chatting process
            println(reservationEvent)


            reservationEventSubscriber.deleteMessage(it)
        }
    }
}