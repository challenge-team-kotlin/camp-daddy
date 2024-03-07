package com.challengeteamkotlin.campdaddy.infrastructure.client.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth2.client.registration.kakao")
data class KakaoRegistrationProperties(
    val clientId: String,
    val clientSecret: String,
    val clientAuthenticationMethod: String,
    val redirectUrl: String,
    val authorizationGrantType: String,
)