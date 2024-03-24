package com.challengeteamkotlin.campdaddy.infrastructure.aws.config

import com.challengeteamkotlin.campdaddy.infrastructure.aws.SnsEventPublisher
import com.challengeteamkotlin.campdaddy.infrastructure.aws.SqsEventSubscriber
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sqs.SqsClient

@Configuration
class AwsEventConfig(
    @Value("\${aws.sns-account.access-key}")
    private var accessKey: String,
    @Value("\${aws.sns-account.secret-key}")
    private var secretKey: String,
    @Value("\${aws.region}")
    private var region: String,
) {
    companion object {
        const val MAX_NUMBER_OF_MESSAGES = 10
        const val WAIT_TIME_SECONDS = 5
    }

    @Bean
    fun snsClient(): SnsClient {
        val awsCredentials: AwsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey)
        return SnsClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .region(Region.of(region))
            .build()
    }

    @Bean
    fun sqsClient(): SqsClient {
        val awsCredentials: AwsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey)
        return SqsClient.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .region(Region.of(region))
            .build()
    }

    @Bean
    fun snsEventPublisher(snsClient: SnsClient): SnsEventPublisher {
        return SnsEventPublisher(snsClient)
    }

    @Bean
    fun sqsEventSubscriber(sqsClient: SqsClient): SqsEventSubscriber {
        return SqsEventSubscriber(sqsClient, MAX_NUMBER_OF_MESSAGES, WAIT_TIME_SECONDS)
    }

}