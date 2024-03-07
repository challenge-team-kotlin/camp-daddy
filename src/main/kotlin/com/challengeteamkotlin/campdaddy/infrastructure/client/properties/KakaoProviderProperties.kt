package com.challengeteamkotlin.campdaddy.infrastructure.client.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth2.client.provider.kakao")
data class KakaoProviderProperties(
    val authorizationUrl: String,
    val tokenUrl: String,
    val userInfoUrl: String,
    val userNameAttribute: String
)