package com.challengeteamkotlin.campdaddy.infrastructure.aws

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest
import java.util.*

class SnsEventPublisher(
    private val snsClient: SnsClient,
) {

    fun publishEvent(groupId:String,topicArn: String, anyObject: Any) {
        return publishEvent(groupId,topicArn, jacksonObjectMapper().writeValueAsString(anyObject))
    }

    private fun publishEvent(groupId:String,topicArn: String, message: String) {
        val request = PublishRequest.builder()
            .topicArn(topicArn)
            .messageGroupId(groupId)
            .message(message)
            .messageDeduplicationId(UUID.randomUUID().toString())
            .build()
        snsClient.publish(request)
    }
}