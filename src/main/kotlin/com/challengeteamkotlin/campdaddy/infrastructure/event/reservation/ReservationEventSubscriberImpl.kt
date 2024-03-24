package com.challengeteamkotlin.campdaddy.infrastructure.event.reservation

import com.challengeteamkotlin.campdaddy.domain.event.reservation.ReservationEventSubscriber
import com.challengeteamkotlin.campdaddy.infrastructure.aws.SqsEventSubscriber
import org.springframework.beans.factory.annotation.Value
import software.amazon.awssdk.services.sqs.model.Message

class ReservationEventSubscriberImpl(
    private val sqsEventSubscriber: SqsEventSubscriber
) : ReservationEventSubscriber {
    @Value("\${aws.sqs-name.reservation-change-queue}")
    lateinit var sqsName: String


    override fun receiveMessages(): List<Message> {
        return sqsEventSubscriber.receiveMessages(sqsName)
    }

    override fun deleteMessage(message: Message) {
        return sqsEventSubscriber.deleteMessage(sqsName, message)
    }
}