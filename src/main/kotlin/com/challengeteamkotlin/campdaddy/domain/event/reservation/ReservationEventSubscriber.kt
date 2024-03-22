package com.challengeteamkotlin.campdaddy.domain.event.reservation

import software.amazon.awssdk.services.sqs.model.Message


interface ReservationEventSubscriber {

    fun receiveMessages(): List<Message>
    fun deleteMessage(message: Message)

}