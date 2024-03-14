package com.challengeteamkotlin.campdaddy.common.config

import com.challengeteamkotlin.campdaddy.infrastructure.jwt.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class PropertiesConfig