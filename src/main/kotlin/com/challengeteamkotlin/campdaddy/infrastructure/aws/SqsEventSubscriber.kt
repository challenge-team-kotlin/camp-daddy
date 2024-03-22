package com.challengeteamkotlin.campdaddy.infrastructure.aws

import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.Message
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest


class SqsEventSubscriber(
    private val sqsClient: SqsClient,
    private val maxNumberOfMessages: Int,
    private val waitTimeSeconds: Int
) {

    fun receiveMessages(queueName: String): List<Message> {
        val queueUrl = sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build()).queueUrl()

        val receiveMessageRequest: ReceiveMessageRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .maxNumberOfMessages(maxNumberOfMessages)
            .waitTimeSeconds(waitTimeSeconds)
            .build()
        return sqsClient.receiveMessage(receiveMessageRequest).messages()
    }

    fun deleteMessage(queueName: String, message: Message) {
        val queueUrl = sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build()).queueUrl()

        val deleteMessageRequest: DeleteMessageRequest = DeleteMessageRequest.builder()
            .queueUrl(queueUrl)
            .receiptHandle(message.receiptHandle())
            .build()
        sqsClient.deleteMessage(deleteMessageRequest)
    }
}