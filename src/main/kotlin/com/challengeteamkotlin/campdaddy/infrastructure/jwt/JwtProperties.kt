package com.challengeteamkotlin.campdaddy.infrastructure.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.jwt")
data class JwtProperties(
    val issuer: String,
    val secret: String,
    val accessTokenExpirationHour: Long,
)
